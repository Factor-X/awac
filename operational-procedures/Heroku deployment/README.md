# AWAC deployment on Heroku: how-to

**Deployment** based on an existing Heroku git repository and using a Play distribution zip file.

## AWAC building procedure

**Build** should be run on latest version **check with git pull**.

Build binary distribution:

```sh
$ rm -r $AWAC_HOME/.tmp
$ rm -r $AWAC_HOME/target
$ play clean update reload dist
```

*Info: the distribution file is generated into *$AWAC_HOME/target/universal/awac-1.0-SNAPSHOT.zip*

## Synchronize Heroku git repository to local repository

**This step can be omitted whether you already have a local repository synchronized with the Heroku one**.
**awac-dev is used as an example **


Get the awac-dev Heroku Git repository

```sh
$ mkdir $SOME_DIR/heroku
$ cd $SOME_DIR/heroku
$ heroku git:clone --app awac-dev
```

## Copy distribution binary file to local git repository

```sh
$ cp *$AWAC_HOME/target/universal/awac-1.0-SNAPSHOT.zip* *$SOME_DIR/heroku/awac-dev*
```

## Commit changes

Add and commit...

```sh
$ git add -A
$ git commit -m "new release"
```

## Set new build pack to Heroku

**This step** only needs to be done once, whether the buildpack is not the correct one.

Set AWAC buildpack...

```sh
$ heroku config:add BUILDPACK_URL=https://github.com/Factor-X/awac-buildpack.git --app awac-dev
```

## Push to Heroku

let's push...

```sh
$ heroku maintenance:on --app awac-dev
$ git push --all
$ heroku maintenance:off --app awac-dev

```

** check logs using:

```sh
$ heroku logs -t --app awac-dev
```

## Help guide

** some tips and hints

** In case heroku:git clone doesnt work

Starting **Deployment** based on an empty Heroku git repository and using a Play distribution zip file.

```sh
$ mkdir $SOME_DIR/heroku/awac-dev
$ cd $SOME_DIR/heroku/awac-dev
$ git init
```

**Add to $SOME_DIR/heroku/awac-dev/.git/config the following definitions:**

[core]
    repositoryformatversion = 0
    filemode = true
    bare = false
    logallrefupdates = true
[remote "awac-dev"]
    url = git@heroku.com:awac-dev.git
    fetch = +refs/heads/:refs/remotes/heroku/
[branch "master"]
    remote = origin
    merge = refs/heads/master

**Copy distribution file to local repository

```sh
$ cp *$AWAC_HOME/target/universal/awac-1.0-SNAPSHOT.zip* *$SOME_DIR/heroku/awac-dev*
```

** Create $SOME_DIR/heroku/awac-dev/Procfile with following content

web: awac-1.0-SNAPSHOT/bin/awac "-Dhttp.port=${PORT} -DapplyEvolutions.default=true -Ddb.default.driver=org.postgresql.Driver -Ddb.default.url=${DATABASE_URL}"

** Add and commit, maintenance mode on, push, maintenance mode off, check logs...

```sh
$ git add –A
$ git commit –m "new release"
$ heroku maintenance:on --app awac-dev
$ git push –all awac-dev
$ heroku maintenance:off --app awac-dev
$ heroku logs -t --app awac-dev

```

## In case heroku instance does not start

** check whether process is running. (using heroku ps)
** if no process use heroku scale

```sh
$ heroku ps --app awac-dev
$ heroku scale web=1
```

** U can also restart Heroku instance using:

```sh
$ heroku restart --app awac-dev
```

## Purge cache

** only apply in case Heroku deplymlent environement seems to be unstable.

```sh
$ heroku repo:purge_cache -a awac-dev
```

## Clean Heroku git repository

** This will erase all content of heroku git repository.
** Once done, only the procedure based on an **empty Heroku git repository** and using a Play distribution zip file can be applied

```sh
$ heroku repo:reset -a awac-dev
```


