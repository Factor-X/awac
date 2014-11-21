#!/bin/bash

# Text colors
txtblk='\e[0;30m' # Black - Regular
txtred='\e[0;31m' # Red
txtgrn='\e[0;32m' # Green
txtylw='\e[0;33m' # Yellow
txtblu='\e[0;34m' # Blue
txtpur='\e[0;35m' # Purple
txtcyn='\e[0;36m' # Cyan
txtwht='\e[0;37m' # White
bldblk='\e[1;30m' # Black - Bold
bldred='\e[1;31m' # Red
bldgrn='\e[1;32m' # Green
bldylw='\e[1;33m' # Yellow
bldblu='\e[1;34m' # Blue
bldpur='\e[1;35m' # Purple
bldcyn='\e[1;36m' # Cyan
bldwht='\e[1;37m' # White
unkblk='\e[4;30m' # Black - Underline
undred='\e[4;31m' # Red
undgrn='\e[4;32m' # Green
undylw='\e[4;33m' # Yellow
undblu='\e[4;34m' # Blue
undpur='\e[4;35m' # Purple
undcyn='\e[4;36m' # Cyan
undwht='\e[4;37m' # White
bakblk='\e[40m'   # Black - Background
bakred='\e[41m'   # Red
bakgrn='\e[42m'   # Green
bakylw='\e[43m'   # Yellow
bakblu='\e[44m'   # Blue
bakpur='\e[45m'   # Purple
bakcyn='\e[46m'   # Cyan
bakwht='\e[47m'   # White
txtrst='\e[0m'    # Text Reset


echo -n -e "${bldgrn}"
echo "================================================================================="
echo "=                                                                               ="
echo "=                                DEPLOYMENT TOOL                                ="
echo "=                                                                               ="
echo "================================================================================="
echo -e "${txtrst}"

# == Setup the local environment for deployment ===============================

# Util-functions
function trim {
    echo "$@" | sed -e 's/^ *//' -e 's/ *$//'
}

function ask {
    local dv=$(eval "echo $(echo -n '$'; echo $(echo $2))")
    echo -n "$1: [$dv] "
    read v
    v=$(trim "$v")
    eval "$2=\"${v:=$dv}\""
}

function save_values {
    echo -n '' > .last_values
    for i in $@
    do
        local cv=$(eval "echo $(echo -n '$'; echo $(echo $i))")
        echo "$i=$cv" >> .last_values
    done
}

function load_values {
    [ -e .last_values ] && source .last_values
}

function trace {
    local dv=$(eval "echo $(echo -n '$'; echo $(echo $1))")
    shift
    local monoline=0
    if [[ "$1" == "-n" ]]; then    
        monoline=1
        shift
    fi

    local a=$(echo "$@")
    if (( "$monoline" )); then
        echo -n -e "${dv}"
        echo -n "${a}"
        echo -n -e "${txtrst}"
    else
        echo -n -e "${dv}"
        echo -n -e "${a}"
        echo -e "${txtrst}"
    fi
}

function info {
    trace txtcyn $@
}

function warn {
    trace txtylw $@
}


function error {
    trace bldred $@
}

function success {
    trace txtgrn $@
}

function title {
    local a=$(echo "$@")
    echo -e "${bldwht}"
    echo -n "== $a "
    printf "%$((76-${#a}))s " | tr ' ' '='
    echo -e "${txtrst}"
}

function sub_title {
    local a=$(echo "$@")
    echo -e "${bldwht}"
    echo -n "-- $a "
    printf "%$((76-${#a}))s " | tr ' ' '-'
    echo -e "${txtrst}"
}

function handle_error {
    error 'error'
    exit
}

# Keep the root path in memory
export ROOT=`pwd`

# Get the current date in a ISO-ish format
export NOW=$(date '+%Y%m%d_%H%M%S')

# read all values from .last_values
load_values



if [[ "$1" == "" ]]; then

    title "1. Perform a backup"

        sub_title "1.1 Local dump"

            ask 'Heroku app name' dep_instance

            db_url="$(heroku config:get DATABASE_URL -a $dep_instance)"

            proto="$(echo $db_url | grep :// | sed -e's,^\(.*://\).*,\1,g')"
            url="$(echo ${db_url/$proto/})"
            user_with_pass="$(echo $url | grep @ | cut -d@ -f1)"
            host_with_port="$(echo ${url/$user_with_pass@/} | cut -d/ -f1)"
            path="$(echo $url | grep / | cut -d/ -f2-)"
            user="$(echo $user_with_pass | cut -d: -f1)"
            pass="$(echo $user_with_pass | cut -d: -f2)"
            host="$(echo $host_with_port | cut -d: -f1)"
            port="$(echo $host_with_port | cut -d: -f2)"

            dump_file="${ROOT}/migrations/dump_${dep_instance}_${NOW}.secret.sql"

            info -n "Dumping database of instance [$dep_instance] into [$dump_file]... "
            (
                export PGPASSWORD=$pass
                pg_dump \
                --no-privileges \
                --no-owner \
                --no-reconnect \
                -h $host \
                -p $port \
                -d $path \
                -U $user \
                -w \
                > $dump_file
            ) && success 'done.' || handle_error



        sub_title "1.2 Backup the dump for archiving"

            info -n "Archiving this copy to the backup directory... "
            (
                [ -e "${ROOT}/database_backups" ] || mkdir "${ROOT}/database_backups" # create directory if not exists
                cp "$dump_file" "${ROOT}/database_backups/${dep_instance}_${NOW}.secret.sql"
            ) && success 'done.' || handle_error
            


    title "2. Restore the dump in the local database"

        yes_no='Y'
        ask 'The local schema awac:public will be dropped (Y/N)' yes_no
        [ $yes_no == 'y' ] || [ $yes_no == 'Y' ] || exit

        export PGPASSWORD=play

        info -n "Dropping schema ... "
        (
            echo "DROP SCHEMA public CASCADE; CREATE SCHEMA public;" | psql -h localhost -U play -d awac -w -q
        ) && success "done" || handle_error

        info "Restoring dump ... "
        (
            psql -h localhost -U play -d awac -w -q < $dump_file > /dev/null
        ) && success "done" || handle_error



    title "3. Insert now your sql, importers, tests, and corrections."

    info "Your file is [$dump_file] <- double-click me to select :)"

    info "When everything is done re-run this script with argument --step4 to go to deployment."

    save_values dep_instance
    exit

fi

if [[ "$1" == "--step4" ]]; then
    title "4. Send the local database to a production instance"

    ask 'Heroku app name' dep_instance

    db_url="$(heroku config:get DATABASE_URL -a $dep_instance)"

    proto="$(echo $db_url | grep :// | sed -e's,^\(.*://\).*,\1,g')"
    url="$(echo ${db_url/$proto/})"
    user_with_pass="$(echo $url | grep @ | cut -d@ -f1)"
    host_with_port="$(echo ${url/$user_with_pass@/} | cut -d/ -f1)"
    path="$(echo $url | grep / | cut -d/ -f2-)"
    user="$(echo $user_with_pass | cut -d: -f1)"
    pass="$(echo $user_with_pass | cut -d: -f2)"
    host="$(echo $host_with_port | cut -d: -f1)"
    port="$(echo $host_with_port | cut -d: -f2)"


    maintenance=$(heroku maintenance -a $dep_instance)
    if [[ "$maintenance" == "off" ]]; then
        warn "It appears the instance in not in maintenance mode".
        yes_no='Y'
        ask "Do you want to activate it now ? (Y/N)" yes_no
        
        if [[ "$yes_no" == 'y' ]] || [[ $yes_no == 'Y' ]]; then
            info -n "Enabling maintenance mode..."
            (
                heroku maintenance:on -a $dep_instance > /dev/null
            ) && success 'done' || handle_error
        fi        
    else
        info "Instance [$dep_instance] is already in maintenance mode."
    fi

    yes_no='N'
    ask 'Are you sure ? THIS CANNOT BE UNDONE (Y/N)' yes_no
    [ $yes_no == 'y' ] || [ $yes_no == 'Y' ] || exit


    dump_file="/tmp/${NOW}.sql"

    info -n "Dumping local database into [$dump_file]... "
    (
        export PGPASSWORD=play
        pg_dump \
        --no-privileges \
        --no-owner \
        --no-reconnect \
        -h localhost \
        -p 5432 \
        -d awac \
        -U play \
        -w \
        > $dump_file
    ) && success 'done.' || handle_error


    export PGPASSWORD=$pass

    info -n "Dropping schema ... "
    (
        echo "DROP SCHEMA public CASCADE; CREATE SCHEMA public;" | psql -h $host -U $user -d $path -p $port -w -q
    ) && success "done" || handle_error

    info "Restoring dump ... "
    (
        psql -h $host -U $user -d $path -p $port -w -q < $dump_file > /dev/null
    ) && success "done" || handle_error



    save_values dep_instance
    exit
fi




if [[ "$1" == "--restore-last" || "$1" == "--rl" ]]; then
    title "1. Restoring last dump"

    dump_file=`(ls -t ./migrations/*.sql | head -1) || echo ''`

    if [[ "$dump_file" == "" ]]; then
        error "No dump found in ./migrations/. Exiting."
        exit
    fi

    info "Last dump is $dump_file"

    export PGPASSWORD='play'

    info -n "Dropping schema ... "
    (
        echo "DROP SCHEMA public CASCADE; CREATE SCHEMA public;" | psql -h localhost -U play -d awac -p 5432 -w -q
    ) && success "done" || handle_error

    info "Restoring dump ... "
    (
        psql -h localhost -U play -d awac -p 5432 -w -q < $dump_file > /dev/null
    ) && success "done" || handle_error

    exit
fi





