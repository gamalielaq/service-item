# service-item  

Balanceo de carga con ribbon

Nota: levantar el mircroservico productos --> branch 2022-002-rest-template

Balanceo de carga ribbon en rest template
 1.- en la clase AppConfig activar la annotation @LoadBalanced
 2.- en la clase ItemController inyectar serviceRestTemplate