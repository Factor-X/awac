export SOME_DIR=/tmp
export AWAC_HOME=$(pwd)

instance=awac-accept

activator clean update compile dist

rm -rf $SOME_DIR/heroku
mkdir -p $SOME_DIR/heroku
cd $SOME_DIR/heroku
heroku git:clone --app $instance

cp "$AWAC_HOME/target/universal/awac-1.0-SNAPSHOT.zip" "$SOME_DIR/heroku/$instance"

cd $SOME_DIR/heroku/$instance
git add -A
git commit -m "new release"

heroku maintenance:on --app $instance
git push --all
heroku maintenance:off --app $instance

heroku logs -t --app $instance

cd $AWAC_HOME
