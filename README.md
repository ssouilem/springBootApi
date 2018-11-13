# Projet sprint Boot Api REST



### Conf database MySQL AWS EC2

1. **sudo yum install mysql-server**

2. **sudo /etc/init.d/mysqld start**

3. **mysql -u root -p**
	> saisir le mot de passe 'new-password' ou ''
	 
4. **Ajouter les droits** 
>GRANT ALL PRIVILEGES ON *.* TO 'ssouilem'@'localhost' IDENTIFIED BY 'direct';
GRANT SELECT ON *.* TO 'login'@'localhost';
CREATE USER 'login'@'%' IDENTIFIED BY 'direct';
GRANT select on DBname.* to 'login'@'%';

5. **Connexion && Create Databases**
 - mysql -u ssouilem -p
- CREATE DATABASE direct;
- SHOW databases;

6.  **sudo /etc/init.d/mysqld stop**
7. **ajouter bind-address = 0.0.0.0**
> sudo vi /etc/my.cnf
	
	[mysqld]
	datadir=/var/lib/mysql
	socket=/var/lib/mysql/mysql.sock
	user=mysql
	port=3306
	bind-address = 0.0.0.0
	#skip-networking
	# Disabling symbolic-links is recommended to prevent assorted security risks
	symbolic-links=0
	
8.  **Redémarrer MySQL** 
> sudo /etc/init.d/mysqld restart

9. sudo /sbin/iptables -A INPUT -i eth0 -p tcp --destination-port 3306 -j ACCEPT
10. **Vérification** 

	netstat -na |grep -i list
	tcp        0      0 0.0.0.0:3306                0.0.0.0:*                   LISTEN

11. **Connexion à la machine distante** 
	mysql -h xxxxxxxxxx.us-xxxx-2.compute.amazonaws.com -P 3306 -u login -p

