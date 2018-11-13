Projet sprint Boot Api REST

### Conf database MySQL AWS EC2

1/ sudo yum install mysql-server
2/ sudo /etc/init.d/mysqld start
3/ mysql -u root -p
saisir le mot de passe 'new-password' ou ''
4/
GRANT ALL PRIVILEGES ON *.* TO 'ssouilem'@'localhost' IDENTIFIED BY 'direct';
GRANT SELECT ON *.* TO 'ssouilem'@'localhost';
CREATE USER 'ssouilem'@'%' IDENTIFIED BY 'direct';
GRANT select on DBname.* to 'ssouilem'@'%';
5/
mysql -u ssouilem -p
CREATE DATABASE direct;
SHOW databases;

6/ sudo /etc/init.d/mysqld stop
7/ sudo vi /etc/my.cnf
	ajouter
	[mysqld]
	datadir=/var/lib/mysql
	socket=/var/lib/mysql/mysql.sock
	user=mysql
	port=3306
	bind-address = 0.0.0.0
	#skip-networking
	# Disabling symbolic-links is recommended to prevent assorted security risks
	symbolic-links=0
	
8/  sudo /etc/init.d/mysqld restart
9/ sudo /sbin/iptables -A INPUT -i eth0 -p tcp --destination-port 3306 -j ACCEPT
10/ netstat -na |grep -i list
tcp        0      0 0.0.0.0:3306                0.0.0.0:*                   LISTEN

11/ mysql -h ec2-52-15-124-186.us-east-2.compute.amazonaws.com -P 3306 -u ssouilem -p

OK
