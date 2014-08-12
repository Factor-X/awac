#/bin/bash


echo ""
echo ""
echo "== Checking app $1..."
heroku apps | awk '{print $1}' | tail -n+2 | grep "$1" || (echo "App $1 not found on heroku" && exit)

echo ""
echo ""
echo "== Cleaning the remote heroku cache..."
heroku repo:purge_cache -a $1

echo ""
echo ""
echo "== Pushing..."
git push $1 master -f

echo ""
echo ""
echo "== Logs"
heroku logs -a $1 -t
