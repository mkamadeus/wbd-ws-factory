# WS-Factory

Java XML Web Service using SOAP Protocols to support Willy Wangky’s Factory App.

## Running the Application

See [dockerfile](./Dockerfile).

Or

1. ```mvn clean install```
2. Copy webapp.war to `$CATALINA_HOME/webapps`.
3. Copy [web.xml](./dependencies/web.xml) to `$CATALINA_HOME/conf`.
4. Copy [jdbc driver](./dependencies/mysql-connector-java-8.0.22.jar) to `$CATALINA_HOME/lib`.

## Pembagian Tugas

### General
1. Autentikasi (Login, Logout, Register)
2. Melihat, menambahkan, mengubah status permintaan add stock baru.
3. Menambahkan jenis coklat baru beserta resep (kebutuhan bahan).
4. Melihat daftar coklat dan melakukan pembuatan coklat tertentu dengan jumlah tertentu.
5. Melihat daftar resep coklat.
6. Melihat dan menambah saldo yang dimiliki pada Factory.
7. Melihat dan menambah bahan dalam gudang.

### Misc
1. Deployment