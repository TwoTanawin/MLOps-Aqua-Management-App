import boto3
import json
import time
import logging
from typing import Dict, Any
import base64


# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

def decode_record(raw_data: bytes) -> Dict[str, Any]:
    """
    Attempt to decode the raw Kinesis record data.
    """
    decoding_methods = [
        lambda x: json.loads(base64.b64decode(x).decode('utf-8')),
        lambda x: json.loads(x.decode('utf-8')),
        lambda x: json.loads(bytes.fromhex(x.hex()).decode('utf-8'))
    ]
    
    for method in decoding_methods:
        try:
            return method(raw_data)
        except Exception as e:
            logger.debug(f"Decoding failed: {e}")
    
    logger.error(f"Failed to decode record: {raw_data}")
    return {}

def process_kinesis_stream(
    stream_name: str = "iot-data-stream", 
    profile_name: str = 'mlops', 
    region_name: str = 'ap-southeast-1',
    max_iterations: int = 100,
    retry_limit: int = 3
):
    """
    Process records from a Kinesis stream with retry logic and error handling.
    """
    session = boto3.Session(profile_name=profile_name, region_name=region_name)
    kinesis_client = session.client("kinesis")

    # Get Shard ID
    shard_id = kinesis_client.describe_stream(StreamName=stream_name)["StreamDescription"]["Shards"][0]["ShardId"]
    logger.info(f"Using Shard ID: {shard_id}")

    # Get the Shard Iterator
    shard_iterator = kinesis_client.get_shard_iterator(StreamName=stream_name, ShardId=shard_id, ShardIteratorType="TRIM_HORIZON")["ShardIterator"]
    
    for _ in range(max_iterations):
        try:
            records_response = kinesis_client.get_records(ShardIterator=shard_iterator, Limit=10)
            shard_iterator = records_response.get("NextShardIterator")

            if not shard_iterator:
                logger.warning("No more data in the stream.")
                break

            if records_response.get("Records"):
                logger.info(f"Found {len(records_response['Records'])} records.")
                for record in records_response["Records"]:
                    try:
                        decoded_data = decode_record(record["Data"])
                        if decoded_data:
                            logger.info(f"Processed Record: {decoded_data}")
                    except Exception as record_error:
                        logger.error(f"Error processing record: {record_error}")
            else:
                logger.info("No records found in this iteration, retrying...")
                time.sleep(1)  # Add a small backoff before retrying.

        except Exception as iteration_error:
            logger.warning(f"Error: {iteration_error}. Retrying...")
            retry_count = 0
            while retry_count < retry_limit:
                retry_count += 1
                time.sleep(1)
                try:
                    records_response = kinesis_client.get_records(ShardIterator=shard_iterator, Limit=10)
                    shard_iterator = records_response.get("NextShardIterator")
                    if shard_iterator:
                        break
                except Exception as retry_error:
                    logger.error(f"Retry attempt {retry_count} failed: {retry_error}")
                    if retry_count == retry_limit:
                        logger.error("Exceeded retry limit. Exiting.")
                        break

def main():
    """Main entry point for the script."""
    try:
        process_kinesis_stream()
    except KeyboardInterrupt:
        logger.info("Stream processing interrupted.")
    except Exception as e:
        logger.error(f"Unhandled error: {e}")

if __name__ == "__main__":
    main()
