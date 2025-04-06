import boto3
import json
import time
import logging

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

def list_partition_keys(
    stream_name: str = "iot-data-stream", 
    profile_name: str = 'mlops', 
    region_name: str = 'ap-southeast-1',
    max_iterations: int = 100
):
    """
    List all unique partition keys from a Kinesis stream.
    """
    session = boto3.Session(profile_name=profile_name, region_name=region_name)
    kinesis_client = session.client("kinesis")

    # Get Shard ID
    shard_id = kinesis_client.describe_stream(StreamName=stream_name)["StreamDescription"]["Shards"][0]["ShardId"]
    logger.info(f"Using Shard ID: {shard_id}")

    # Get the Shard Iterator starting from the latest data
    shard_iterator = kinesis_client.get_shard_iterator(
        StreamName=stream_name,
        ShardId=shard_id,
        ShardIteratorType="LATEST"
    )["ShardIterator"]
    
    # Set to store unique partition keys
    unique_partition_keys = set()
    
    for _ in range(max_iterations):
        try:
            # Retrieve records from the stream
            records_response = kinesis_client.get_records(ShardIterator=shard_iterator, Limit=10)
            shard_iterator = records_response.get("NextShardIterator")

            if not shard_iterator:
                logger.warning("No more data in the stream.")
                break

            if records_response.get("Records"):
                logger.info(f"Found {len(records_response['Records'])} records.")
                for record in records_response['Records']:
                    # Collect partition keys
                    partition_key = record['PartitionKey']
                    unique_partition_keys.add(partition_key)
                    logger.info(f"Found Partition Key: {partition_key}")
            else:
                logger.info("No records found in this iteration.")
                break

        except Exception as iteration_error:
            logger.error(f"Error retrieving records: {iteration_error}")
            break

    # Print summary of unique partition keys
    print("\n--- Unique Partition Keys ---")
    for key in unique_partition_keys:
        print(key)
    
    print(f"\nTotal Unique Partition Keys Found: {len(unique_partition_keys)}")

def main():
    """Main entry point for the script."""
    try:
        list_partition_keys()
    except KeyboardInterrupt:
        logger.info("Stream processing interrupted.")
    except Exception as e:
        logger.error(f"Unhandled error: {e}")

if __name__ == "__main__":
    main()