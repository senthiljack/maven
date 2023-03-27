FROM nginx
WORKDIR /usr/share/nginx/html/
COPY index.html /usr/share/nginx/html/index.html
COPY default.conf /etc/nginx/conf.d/default.conf 


