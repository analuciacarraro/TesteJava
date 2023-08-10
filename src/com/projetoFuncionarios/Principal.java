import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        String dbType = "sqlserver"; // ou "oracle"
        String url = "localhost:1433"; // ou "localhost:1521/xe" para Oracle
        String user = "seu_usuario";
        String password = "sua_senha";

        try {
            Connection connection = DatabaseConnection.getConnection(dbType, url, user, password);

            if (connection != null) {
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);
                List<Funcionario> funcionarios = funcionarioDAO.buscarFuncionarios();
        

         // Remover o funcionário "João"
         funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

         // Imprimir todos os funcionários
         System.out.println("Funcionários:");
         funcionarios.forEach(System.out::println);
 
         // Aumentar salário em 10%
         funcionarios.forEach(funcionario -> funcionario.setSalario(funcionario.getSalario().multiply(new BigDecimal("1.10"))));
 
         // Agrupar por função
         Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                 .collect(Collectors.groupingBy(Funcionario::getFuncao));
 
         // Imprimir funcionários agrupados por função
         System.out.println("\nFuncionários por função:");
         funcionariosPorFuncao.forEach((funcao, lista) -> {
             System.out.println("Função: " + funcao);
             lista.forEach(System.out::println);
         });
 
         // Imprimir funcionários com aniversário em outubro (mês 10) e dezembro (mês 12)
         int mesAniversario1 = 10;
         int mesAniversario2 = 12;
         System.out.println("\nFuncionários com aniversário em outubro e dezembro:");
         funcionarios.stream()
                 .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == mesAniversario1 ||
                         funcionario.getDataNascimento().getMonthValue() == mesAniversario2)
                 .forEach(System.out::println);
 
         // Encontrar o funcionário mais velho
         Funcionario funcionarioMaisVelho = funcionarios.stream()
                 .min(Comparator.comparing(funcionario -> funcionario.getDataNascimento()))
                 .orElse(null);
 
         // Calcular idade do funcionário mais velho
         int idadeMaisVelho = LocalDate.now().getYear() - funcionarioMaisVelho.getDataNascimento().getYear();
 
         // Imprimir funcionário mais velho
         System.out.println("\nFuncionário mais velho:");
         System.out.println("Nome: " + funcionarioMaisVelho.getNome() + ", Idade: " + idadeMaisVelho);
 
         // Ordenar funcionários por nome alfabeticamente
         funcionarios.sort(Comparator.comparing(Funcionario::getNome));
 
         // Imprimir funcionários em ordem alfabética
         System.out.println("\nFuncionários em ordem alfabética:");
         funcionarios.forEach(System.out::println);
 
         // Calcular total dos salários
         BigDecimal totalSalarios = funcionarios.stream()
                 .map(Funcionario::getSalario)
                 .reduce(BigDecimal.ZERO, BigDecimal::add);
 
         // Imprimir total dos salários
         System.out.println("\nTotal dos salários: " + totalSalarios.toString());
 
         // Calcular quantos salários mínimos cada funcionário ganha
         BigDecimal salarioMinimo = new BigDecimal("1212.00");
         System.out.println("\nSalários em relação ao salário mínimo:");
         funcionarios.forEach(funcionario -> {
             BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
             System.out.println(funcionario.getNome() + ": " + salariosMinimos + " salários mínimos");
         });
         connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
