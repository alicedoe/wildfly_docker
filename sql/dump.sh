mysqldump -h172.17.0.2 -uroot -prootpassword dbschema > dump_$(date +"%d-%b-%Y").sql
