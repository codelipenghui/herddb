#/bin/bash

HERE=$(dirname $0)
HERE=$(realpath $HERE)
YCSB_PATH=$1
HERDDB_PATH=$(realpath $2)
WORKLOAD=$3
JDBC_DRIVER=$(ls $HERDDB_PATH/*jdbc*.jar)

echo "Running YCSB workload $WORKLOAD from $YCSB_PATH"
echo "Using HerdDB instance at $HERDDB_PATH"
echo "Using JDBC Driver $JDBC_DRIVER"

if [[ ! -d "$HERDDB_PATH" ]]; then
   echo "Directory $HERDDB_PATH is not a valid directory"
   exit 1
fi
if [[ ! -d "$YCSB_PATH" ]]; then
   echo "Directory $YCSB_PATH is not a valid directory"
   exit 1
fi


$HERDDB_PATH/bin/service server start
sleep 5
$YCSB_PATH/bin/ycsb run jdbc -P $YCSB_PATH/workloads/$WORKLOAD -P $HERE/herd.properties -cp $HERDDB_PATH/herddb-jdbc* -threads 200 -s

$HERDDB_PATH/bin/service server stop
