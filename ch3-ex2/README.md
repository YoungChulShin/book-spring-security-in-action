# 설명

# 사용
## 의존성 설정
mysql과 jdbc 사용을 위해서 아래 2개의 의존성을 추가한다. 
```groovy
implementation 'org.springframework.boot:spring-boot-starter-jdbc'
implementation 'mysql:mysql-connector-java:8.0.33'
```
## 서버 실행 시 sql 실행
서버가 실행 될 때, ddl/dml sql을 실행할 수 있는 방법을 설명한다. 
1. ddl/dml 쿼리를 각각 `schema.sql`, `data.sql` 파일에 작성한다.
2. 작성한 파일을 `resources` 폴더에 넣는다.
3. `application.yaml` 파일에 sql init mode를 설정한다. 
   ```yaml
   spring:
    sql:
      init:
        mode: always
   ```