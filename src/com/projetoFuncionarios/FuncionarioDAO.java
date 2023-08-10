import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    private String url;
    private String user;
    private String password;

    public FuncionarioDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public List<Funcionario> buscarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM funcionarios")) {

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                LocalDate dataNascimento = resultSet.getDate("data_nascimento").toLocalDate();
                BigDecimal salario = resultSet.getBigDecimal("salario");
                String funcao = resultSet.getString("funcao");
                funcionarios.add(new Funcionario(nome, dataNascimento, salario, funcao));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funcionarios;
    }
}
