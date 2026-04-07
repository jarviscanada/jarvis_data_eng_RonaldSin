psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

specs=`lscpu`

hostname=$(hostname -f)
cpu_number=$(echo "$specs" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$specs" | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$specs" | egrep "^Model name:" |awk '{print $3" "$4" "$5" "$6" "$7}' | xargs)
cpu_mhz=$(echo "$specs" | egrep "^Model name:" |awk '{print $7}' | head -c-4 | awk '{printf "%.3f", $1 * 1000}' | xargs)
l2_cache=$(echo "$specs" | egrep "^L2" | awk '{print $3}' | xargs)
total_mem=$(vmstat --unit M | tail -1 | awk '{print $4}')
timestamp=$(date +"%Y-%m-%d %H:%M:%S")

insert_stmt="INSERT INTO host_info(hostname, \
              cpu_number, \
              cpu_architecture, \
              cpu_model, \
              cpu_mhz, \
              l2_cache, \
              \"timestamp\", \
              total_mem) VALUES('$hostname', \
              $cpu_number, \
              '$cpu_architecture', \
              '$cpu_model', \
              $cpu_mhz, \
              $l2_cache, \
              '$timestamp', \
              '$total_mem')"

export PGPASSWORD=$psql_password
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"

exit $?

