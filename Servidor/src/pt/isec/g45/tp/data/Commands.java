/**
 *  @author Bruno Sousa
 *  @author Jorge Santos
 *  @author João Baptista
 * This file stores the majority of sql commands used in this project. These commands are meant for SQLlite.
 * It is divided by 2 sections:
 *   1 - Constant Strings Holding commands syntax
 *   2 - Methods that format the commands, so we can receive arguments and execute them.
 *
**/
package pt.isec.g45.tp.data;



public abstract class Commands {

    // ESPETÁCULO
    static final String REGIST_SHOW = "INSERT INTO espetaculo (descricao, tipo, data_hora, duracao, local, localidade, pais, classificacao_etaria, visivel) VALUES(";
    // Espetáculo - Getters
    static final String SELECT_AVAILABLE_SHOWS = "SELECT * FROM espetaculo WHERE visivel = 1";
    static final String SELECT_FILTERED_SHOWS = "SELECT * FROM espetaculo WHERE";
    static final String GET_ESPETACULO = "SELECT * FROM espetaculo WHERE id =";
    static final String GET_ESPETACULO_ID_BY_NAME = "SELECT id FROM espetaculo WHERE descricao = '";
    static final String GET_DESCRICAO = "SELECT descricao FROM espetaculo WHERE id = ";
    // Espetáculo - Setters
    static final String SET_SHOW_VISIBILITY = "UPDATE espetaculo SET visivel=";

    // LUGAR
    static final String SET_SITS_SHOW = "INSERT INTO lugar (fila, assento, preco, espetaculo_id) VALUES(";
    // Lugar - Getters
    static final String GET_AVAILABLE_SITS_FROM_SHOW = "SELECT * FROM lugar l WHERE espetaculo_id =";
    static final String GET_SITS_FROM_SHOW = "SELECT * FROM lugar l WHERE espetaculo_id =";

    static final String REGIST_SIT = "INSERT INTO reserva_lugar (id_reserva, id_lugar) VALUES (";

    // RESERVA
    // Reserva - Getters
    static final String GET_NOT_PAID_RESERVATIONS = "SELECT * FROM reserva WHERE pago = 0";
    static final String GET_PAID_RESERVATIONS = "SELECT * FROM reserva WHERE pago = 1";
    // Reserva - Setters
    static final String REGIST_RESERVE = "INSERT INTO reserva(data_hora, pago, id_utilizador, id_espetaculo) VALUES(";
    static final String SET_RESERVA_PAGO = "UPDATE reserva SET pago=";
    static final String DELETE_NOT_PAID_RESERVATION = "DELETE FROM reserva WHERE pago = 0 and id = ";
    static final String DELETE_SHOW = "DELETE FROM espetaculo where id=";
    // RESERVA_LUGAR
    // Reserva_Lugar - Getters
    // Reserva_Lugar - Setters
    static final String REGIST_SIT_RESERVE = "INSERT INTO reserva_lugar(id_reserva, id_lugar) VALUES(";

    // UTILIZADOR
    static final String REGIST_ADMIN = "INSERT INTO utilizador (username, nome, password, administrador) VALUES (";
    static final String REGIST_USER = "INSERT INTO utilizador (username, nome, password) VALUES (";
    // Utilizador - Getters
    static final String GET_PASSWORD = "SELECT password FROM utilizador WHERE username = '";
    // Utilizador - Setters
    static final String SET_USERNAME = "UPDATE utilizador SET username = '";
    static final String SET_NAME = "UPDATE utilizador SET nome = '";
    static final String SET_PASSWORD = "UPDATE utilizador SET password = '";
    static final String SET_AUTENTICADO = "UPDATE utilizador SET autenticado=";

    // VERSÃO
    static final String CREATE_TABLE_VERSION = "CREATE TABLE VERSAO(versao INTEGER PRIMARY KEY); ";
    // Versão - Getter
    static final String GET_DB_VERSION = "SELECT * FROM versao";
    // Versão - Setter
    static final String SET_DB_VERSION = "UPDATE versao SET versao=";
    static final String GET_USER_ISADMIN = "SELECT administrador FROM utilizador WHERE username = '";



    // ESPETÁCULO
    static String registShow(String descricao, String tipo, String data_hora,
                             int duracao, String local, String localidade,
                             String pais, String classificacao_etaria, int visivel) {
        return REGIST_SHOW + String.format("'%s', '%s', '%s', %d, '%s', '%s', '%s', '%s', %d)",
                descricao, tipo, data_hora, duracao, local, localidade, pais, classificacao_etaria, visivel);
    }

    // RESERVA
    static String registNewReserve(String data_hora, int id_utilizador, int id_espetaculo) {
        return String.format("'%s', '%d', '%d', '%d')", data_hora, 0, id_utilizador, id_espetaculo);
    }

    //LUGAR
    static String registSitShow(String fila, int assento, int preco, int id_espetaculo) {
        return SET_SITS_SHOW + String.format("'%s', '%d', '%d', '%d')", fila, assento, preco, id_espetaculo);
    }

    // UTILIZADOR
    static String registCommandAdmin(String username, String password){
        return String.format("'%s', '%s', '%s', 1)", username, username, password);
    }
    static String registCommand(String username, String password, String name){
        return String.format("'%s', '%s', '%s')", username, name, password);
    }
    static String setUsername(String oldUsername, String newUsername){
        return String.format("%s' WHERE username = '%s' ", newUsername, oldUsername);
    }
    static String setName(String username, String newName){
        return String.format("%s' WHERE username = '%s' ", newName, username);
    }
    static String setPassword(String username, String newPassword){
        return String.format("%s' WHERE username = '%s' ", newPassword, username);
    }

    // ESPETÁCULO & RESERVA
    static String getDescricao(int id){
        return GET_DESCRICAO + id;
    }

    // autenticacao
    static String setAutenticado(String username) {
        return String.format(" WHERE username = '%s' ", username);
    }
    static String getSelectFilteredShows(String filtro, String nome) {
        return String.format(SELECT_FILTERED_SHOWS + " "+filtro+"= '"+nome+"' and visivel=1");
    }

    static String getIdReserva(int id_espetaculo, int id_utilizador) {
        return String.format("SELECT id FROM reserva WHERE id_utilizador = " + id_utilizador + " AND id_espetaculo = " + id_espetaculo);
    }

    static String registSit(int id_reserva, int id_lugar){
        return REGIST_SIT + String.format("'%d', '%d')", id_reserva, id_lugar);
    }

    static String getIdUsername(String username) {
        return String.format("SELECT id FROM utilizador WHERE username = '" + username + "'");
    }

    static String getAvailableSitsFromShow(int id_espetaculo) {
        return String.format("SELECT * FROM lugar, (SELECT id_lugar exclusao FROM reserva_lugar) tab WHERE espetaculo_id = %d AND id NOT IN(tab.exclusao)", id_espetaculo);
        //return String.format("SELECT * FROM lugar WHERE espetaculo_id = %d MINUS SELECT * FROM lugar, reserva_lugar WHERE lugar.id = reserva_lugar.id_lugar", id_espetaculo);
    }
}
