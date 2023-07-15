CREATE
USER 'root'@'%' IDENTIFIED by 'root';
CREATE
USER 'minigoodreads'@'%' IDENTIFIED BY 'minigoodreads';

GRANT ALL PRIVILEGES ON *.* TO
'root'@'%';
GRANT ALL PRIVILEGES ON *.* TO
'minigoodreads'@'%';