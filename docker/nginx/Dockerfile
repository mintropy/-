FROM nginx:latest

# RUN rm /etc/nginx/conf.d/default.conf
# COPY nginx.conf /etc/nginx/conf.d
COPY docker/nginx/default.conf /etx/nginx/conf.d/default.conf

CMD ["nginx", "-g", "daemon off;"]