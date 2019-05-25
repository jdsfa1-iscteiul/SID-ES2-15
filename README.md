-------------------------------------------------------------
INSTRUÇÕES PASSO A PASSO PARA DOWNLOAD, INSTALAÇÃO E EXECUÇÃO
-------------------------------------------------------------

Requisitos:

- Aceder aos links, transferir e instalar o software de acordo com o sistema operativo da máquina hospedeira.

Xampp:
https://www.apachefriends.org/download.html

Java Runtime Environment 8
https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html

-------------------------------------------------------------------------------------------------------------

Preparação:

- Adicionar à variável de ambiente PATH o caminho para [...]/jre1.8.0/bin, ou definir a variável de ambiente JAVA_HOME
[Nota: Caso já tenha o caminho para outra versão do JRE ou JDK que não a 1.8.0, na variável de ambiente PATH, deverá à mesma adicionar o novo caminho e colocá-lo num lugar acima do caminho prévio]

- Efectuar o download da base de dados "grupo15_culture_management.sql" e do jar "CultureManagement-1.0.jar" do GitHub, no link que se segue:
https://github.com/jdsfa1-iscteiul/SID-ES2-15/tree/master/CultureManagement

- Abrir o Xampp e iniciar os serviços "Apache" e "MySQL" no painel de controlo da aplicação.
[Nota: o porto 3306 para o serviço MySQL não deverá ser alterado, sob pena de não funcionamento da aplicação. Os portos do Apache podem ser redefinidos, consoante vontade do utilizador.]

----------------------------------------------------------------------------------------------------------------------------------------

Instalação e execução:

1º Já com os serviços "Apache" e "MySQL" iniciados no Xampp, abrir uma janela do seu browser e aceder a:
http://localhost:[nº porto Apache]/phpmyadmin/
[Nota: "[nº porto Apache]" deverá ser substituído pelo porto que estiver definido no serviço "Apache" do Xampp]

2º Realizar log in com o user "root", caso seja pedido.

3º No menu lateral esquerdo, clicar em "New" e criar uma nova base de dados com o nome "grupo15_culture_management", finalizando a operação com no botão "Criar".

3º Selecionar a base de dados criada no passo anterior, no menu lateral esquerdo.

4º Clicar em "Importar" no menu superior e, de seguida, clicar em "Escolher ficheiro" selecionando o ficheiro "grupo15_culture_management.sql", do qual fez download na preparação. Finalizar com clique em "Executar" no fim da página.

5º Aceder aos procedures clicando no "+" no menu do lado esquerdo, ao lado do nome da base de dados "grupo15_culture_management" seguido de um clique em "Procedures".

6º Executar o procedure "create_administrator_role" clicando no botão "Executar" que aparece na linha deste procedure.
[Nota: este procedure cria um utilizador na base de dados com user: "admin" e password: "admin"]

7º Para executar a aplicação, abrir a linha de comandos e executar o comando "java -jar [caminho para o ficheiro CultureManagement-1.0.jar]"
[Nota: "[caminho para o ficheiro Cultureanagement-1.0.jar]" deverá ser substituído pelo caminho do ficheiro em questão]

----------------------------------------------------------------------------------------------------------------------------------------

Autoria:

Grupo 15 - Engenharia de Software II - 2018/2019 - ISCTE

Beatriz Roque, 64955, LEI-PL </br> 
João Figueira, 78308 , LEI-PL </br> 
Marco Domingues, 78726, LEI-PL </br> 
Rúben Agostinho, 78123 , LEI-PL </br> 
Sílvia Sendim, 78262, LEI-PL </br> 
Vasco Faias, 77623, LEI-PL </br> 
