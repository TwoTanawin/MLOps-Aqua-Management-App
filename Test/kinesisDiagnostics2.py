import boto3
import json
import logging
import base64

logging.basicConfig(level=logging.INFO, 
                    format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

def comprehensive_kinesis_diagnostics(profile_name='mlops'):
    try:
        # Create a session with a specific profile
        session = boto3.Session(profile_name=profile_name, region_name='ap-southeast-1')

        # AWS Clients using the session
        iot_client = session.client('iot')
        kinesis_client = session.client('kinesis')

        # 1. Check IoT Rule Details
        rules = iot_client.list_topic_rules()
        for rule in rules['rules']:
            if rule['ruleName'] == 'rule1':
                logger.info("IoT Rule Details:")
                rule_details = iot_client.get_topic_rule(ruleName='rule1')
                actions = rule_details['rule']['actions']
                
                for action in actions:
                    if 'kinesis' in action:
                        logger.info(f"Kinesis Action Details: {json.dumps(action['kinesis'], indent=2)}")

        # 2. Stream Detailed Diagnostics
        stream_name = 'iot-data-stream'
        stream_description = kinesis_client.describe_stream(StreamName=stream_name)
        
        logger.info("\nDetailed Stream Information:")
        logger.info(f"Stream ARN: {stream_description['StreamDescription']['StreamARN']}")
        logger.info(f"Stream Status: {stream_description['StreamDescription']['StreamStatus']}")
        
        # 3. Shard Analysis
        for shard in stream_description['StreamDescription']['Shards']:
            shard_id = shard['ShardId']
            logger.info(f"\nShard ID: {shard_id}")
            
            try:
                # Get Iterator
                shard_iterator = kinesis_client.get_shard_iterator(
                    StreamName=stream_name,
                    ShardId=shard_id,
                    ShardIteratorType='LATEST'
                )['ShardIterator']
                
                # Get Records
                records_response = kinesis_client.get_records(
                    ShardIterator=shard_iterator, 
                    Limit=10
                )
                
                logger.info(f"Records Found: {len(records_response['Records'])}")
                
                # Decode and Log Records
                for idx, record in enumerate(records_response['Records'], 1):
                    try:
                        decoded_data = base64.b64decode(record['Data']).decode('utf-8')
                        logger.info(f"Record {idx} Raw Data: {decoded_data}")
                    except Exception as decode_error:
                        logger.error(f"Record {idx} decoding error: {decode_error}")
            
            except Exception as shard_error:
                logger.error(f"Shard processing error: {shard_error}")

    except Exception as e:
        logger.error(f"Diagnostic error: {e}")

if __name__ == "__main__":
    comprehensive_kinesis_diagnostics()