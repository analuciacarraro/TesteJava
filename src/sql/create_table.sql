CREATE TABLE Funcionarios (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Nome NVARCHAR(100),
    DataNascimento DATE,
    Salario DECIMAL(10, 2),
    Funcao NVARCHAR(50)
);
