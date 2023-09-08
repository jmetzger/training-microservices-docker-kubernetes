# Build image of reservations on gitlab ci-cd 

## Step 1: Clone Repo from github locally

```
cd
git clone https://github.com/jmetzger/ms-reservations.git
cd ms-reservations
```

## Step 2: Change origin (target where push data) and push 

```
# of your newly created repo on gitlab 
git remote -v
git remote set-url origin https://gitlab.com/training.tn1/ms-jochen.git
# find out current branch and use it in next step
# marked with a *
git branch
# enter username + password 
git push -u origin master 
```

