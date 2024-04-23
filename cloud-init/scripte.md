# Script zum Einrichten eines Clients mit mehreren Benutzer 

```
#!/bin/bash 

groupadd sshadmin
USERS="11trainingdo $(echo tln{1..20})"
echo $USERS
for USER in $USERS
do
  echo "Adding user $USER"
  useradd -s /bin/bash --create-home $USER
  usermod -aG sshadmin $USER
  echo "$USER:deinpassword" | chpasswd
done

# We can sudo with 11trainingdo
usermod -aG sudo 11trainingdo 

# Now let us do some generic setup 
echo "Installing kubectl"
snap install --classic kubectl

echo "Installing helm"
snap install --classic helm 



# 20.04 and 22.04 this will be in the subfolder
if [ -f /etc/ssh/sshd_config.d/50-cloud-init.conf ]
then
  sed -i "s/PasswordAuthentication no/PasswordAuthentication yes/g" /etc/ssh/sshd_config.d/50-cloud-init.conf
fi

## both is needed 
sed -i "s/PasswordAuthentication no/PasswordAuthentication yes/g" /etc/ssh/sshd_config

usermod -aG sshadmin root

# TBD - Delete AllowUsers Entries with sed 
# otherwice we cannot login by group 

echo "AllowGroups sshadmin" >> /etc/ssh/sshd_config 
systemctl reload sshd 

### BASH Completion ###
# update repo 
apt-get update 
apt-get install -y bash-completion
source /usr/share/bash-completion/bash_completion
# is it installed properly
type _init_completion

# 1. kubectl completion -> activate for all users
kubectl completion bash | sudo tee /etc/bash_completion.d/kubectl > /dev/null

# 2. helm completion -> activate for all users 
helm completion bash | sudo tee /etc/bash_completion.d/helm > /dev/null


# Activate syntax - stuff for vim
# Tested on Ubuntu 
echo "hi CursorColumn cterm=NONE ctermbg=lightred ctermfg=white" >> /etc/vim/vimrc.local 
echo "autocmd FileType y?ml setlocal ts=2 sts=2 sw=2 ai number expandtab cursorline cursorcolumn" >> /etc/vim/vimrc.local 

# Activate Syntax highlightning/autoindenting for nano 
# v1 - old version / remove if new version works 
#cd /usr/local/bin
#git clone https://github.com/serialhex/nano-highlight.git 
# Now set it generically in /etc/nanorc to work for all 
#echo 'include "/usr/local/bin/nano-highlight/yaml.nanorc"' >> /etc/nanorc 

####################################
# v2 - new version / more simplistic
#################################### 
echo "include /usr/share/nano/yaml.nanorc" >> /etc/nanorc 
echo "set autoindent" >> /etc/nanorc
echo "set tabsize 2" >> /etc/nanorc
echo "set tabstospaces" >> /etc/nanorc 

# Install nfs-common for mounting, just in case we need it for persistant storage exercise 
apt-get install -y nfs-common

```
