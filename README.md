# ClickBackendAssesment
Click Backend Assesment

1. Clone the project

  git clone https://github.com/CypraxPuch/ClickBackendAssesment.git

2. mvn install

3. edit application.sh to change the location where the jar file is.

4. give permissions to application.sh

    chmod u+x application.sh

5. execute operation

IMPORTANT: if the shell script execution doesn't work, maybe you need to run this command:

[user@host]$ dos2unix application.sh


I just realized that the application.sh was writed on windows, so, sometimes problems happens with some characters.




Permitted Operations:

ADD		./application <user_id> add <transaction_json>

SHOW	./application <user_id> <transaction_id>

LIST	./application <user_id> list

SUM		./application <user_id> sum

