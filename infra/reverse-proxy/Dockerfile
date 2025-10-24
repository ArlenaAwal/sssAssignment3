# Build frontend
FROM node:20-alpine AS fe
WORKDIR /src
COPY frontend/ /src/
RUN npm ci || npm install
RUN npm run build

# NGINX serves static + proxies /api
FROM nginx:1.27-alpine
COPY infra/reverse-proxy/nginx.conf /etc/nginx/nginx.conf
COPY infra/reverse-proxy/tls/ /etc/nginx/tls/
COPY --from=fe /src/dist /usr/share/nginx/html
