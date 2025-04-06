import boto3
import json
import logging

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

def diagnose_kinesis_stream(stream_name='iot-data-stream', profile_name='mlops', region_name='ap-southeast-1'):
    try:
        # Create session
        session = boto3.Session(profile_name=profile_name, region_name=region_name)
        kinesis_client = session.client('kinesis')

        # Describe Stream
        stream_description = kinesis_client.describe_stream(StreamName=stream_name)
        
        logger.info("Stream Status:")
        logger.info(f"Stream Name: {stream_name}")
        logger.info(f"Stream Status: {stream_description['StreamDescription']['StreamStatus']}")
        logger.info(f"Retention Period: {stream_description['StreamDescription']['RetentionPeriodHours']} hours")
        
        # Shard Information
        shards = stream_description['StreamDescription']['Shards']
        logger.info(f"\nTotal Shards: {len(shards)}")
        
        for shard in shards:
            logger.info(f"\nShard ID: {shard['ShardId']}")
            
            try:
                # Get Latest Shard Iterator
                shard_iterator = kinesis_client.get_shard_iterator(
                    StreamName=stream_name,
                    ShardId=shard['ShardId'],
                    ShardIteratorType='LATEST'
                )['ShardIterator']
                
                # Try to get records
                records_response = kinesis_client.get_records(ShardIterator=shard_iterator, Limit=10)
                
                logger.info(f"Records in Shard: {len(records_response['Records'])}")
                
                if records_response['Records']:
                    for idx, record in enumerate(records_response['Records'], 1):
                        try:
                            decoded_data = json.loads(record['Data'].decode('utf-8'))
                            logger.info(f"Record {idx} Data: {decoded_data}")
                        except Exception as decode_error:
                            logger.error(f"Record {idx} decoding error: {decode_error}")
                
            except Exception as shard_error:
                logger.error(f"Error processing shard {shard['ShardId']}: {shard_error}")

    except Exception as e:
        logger.error(f"Diagnostic error: {e}")

if __name__ == "__main__":
    diagnose_kinesis_stream()