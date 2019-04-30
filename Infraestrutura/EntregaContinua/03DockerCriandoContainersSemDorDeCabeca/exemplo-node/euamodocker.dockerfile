# Lembre-se de que o dockerfile construi IMAGENS e não containers.
FROM node:latest # imagem de origem
MAINTAINER Denis Klein
ENV PORT=3000 # variável de ambiente
COPY . /var/www # copia um conteúdo para a imagem
WORKDIR /var/www
RUN npm install # executa durante a contrução da imagem.
ENTRYPOINT npm start # comanto executado ao iniciar o container (run ou start)
EXPOSE $PORT # expõe porta (???)
# A imagem é construída com o comando docker build -t "{NOME DA IMAGEM}" .
# O "." faz parte do comando e diz que o dockerfile que cria a imagem está no seu diretório atual.
