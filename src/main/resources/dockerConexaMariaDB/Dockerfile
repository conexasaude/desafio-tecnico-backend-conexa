# MariaDB 10.3 with SSH
# Pull the mysql latest image
FROM mariadb:latest
# List all the packages that we want to install
ENV PACKAGES openssh-server openssh-client
# Install Packages
RUN apt-get update && apt-get install -y $PACKAGES
# Allow SSH Root Login
RUN sed -i 's|^#PermitRootLogin.*|PermitRootLogin yes|g' /etc/ssh/sshd_config
# Configure root password
RUN echo "root:root123" | chpasswd
ENV MARIADB_ROOT_PASSWORD=root123
# Restore RNAJ dump
ENV MYSQL_DATABASE=conexa
ADD init/init_db.sh /tmp/init_db.sh
RUN chmod +x /tmp/init_db.sh

EXPOSE 3306
