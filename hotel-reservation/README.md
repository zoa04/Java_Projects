# Hotel Reservation System (Java - Maven)

Projeto de exemplo em Java para um sistema de reservas de hotel com funcionalidades:
- Cadastro de hóspedes
- Gestão de quartos
- Criação e cancelamento de reservas
- Verificação de disponibilidade
- Geração de relatórios (CSV)

Como rodar:
1. Tenha Java 11+ e Maven instalados.
2. No diretório do projeto: `mvn compile exec:java -Dexec.mainClass="com.hotel.Main"`
(ou empacote com `mvn package` e execute o jar)

Arquivos de dados são salvos em `data/` como JSON para persistência simples.
