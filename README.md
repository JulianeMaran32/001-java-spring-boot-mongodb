# API RESTful with Spring Boot and MongoDB  

* IDE: Spring Tool Suite for Eclipse    
* Database: MongoDB    
* Package: Project Lombok  
* Postman   

<h2> Class UserResource </h2>

> Método GET findAll lista todos os usuários   
> `ResponseEntity` encapsula toda uma estrutura que retorna response HTTP com possíveis erros e cabeçalhos  
> `List<User> list = userService.findAll();`: busca no banco de dados os usuarios  eguarda ela    
> `return ResponseEntity.ok().body(list);`  
> `ok()`: instância o ResponseEntity já com o código de resposta HTTP que é SUCCESS   
> `body()`: trás no corpo da requisição a lista 
> 

* listDto 

> `listDTO` recebe a conversao de cada elemento da lista original para dto; instrução lambda que vai ser: lista original ponto stream, para transformar em uma `.stream()`, que é uma coleção compatível com as expressões **lambda**; chamar o método `.map()` que vai pegar cada objeto x da lista original e para cada objeto desse que será um usuário vamos retornar um novo `UserDTO` e passando o x como argumento; para finalizar, precisamos voltar o `.stream()` para uma lista.

<h2> Class UserRepository </h2>

> Faz as operações básicas como adicionar, criar, deletar, atualizar etc.     

<h2> Class UserService </h2>

> serviço responsável por trabalhar com usuários    
> Service conversa com o Repository

<h2> Database: MongoDB </h2>

> Para que o MongoDB reconheça a classe, devemos incluir `@Document` em cima da classe User e `@Id` logo acima do atributo id, também da classe User.  
> A camada REST conversa com o Service   

<h2> Dependency: Project Lombok</h2>

> Anotações que fornecem os métodos `getters` e `setters` para atributos privados.  
> Essas anotaçõe podem ter um parâmetros para indicar o tipo de acesso aos dados (public ou protected).   

```java
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
```
  
> `@NonNull`serve para fornecer um teste rápido para verificar se um atributo é nulo, auxiliando a evitar o `NullPointerException`  
>  
> `@ToString` gera uma implementação do método toString.  
>   
> `@EqualsAndHashCode` fornece os métodos `equals()`e `hashCode()`.  
> Podendo ser personalizado os atributos que serão validos.  

```java
@EqualsAndHashCode(exclude{"dataNascimento", "idade"})
```

>  
> `@Data` é a combinação das anotações: `@Getters`, `@Setters`, `@ToString` e `@EqualsAndHashCode`.   
>   
> `@AllArgsConstructor` - construtor com todos os atributos. É possível definir o tipo de acesso do construtor gerado.    

```java
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
```

>  
> `@NoArgsConstructor` - construtor vazio   

Exemplo, utilizando toda as anotações citadas acima:  


```java
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

	private String id;
	private String nome;
	private String email;

}
```

> `@RequiredArgsConstructor` útil para fazer injeção de dependência
     
```java
@Component
@RequiredArgsConstructor
public class User{

	private String id;
	private String nome;
	private String email;

}
```

> `@Slf4j`: Cria o atributo `private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger (PessoaGatewayImpl.class)`  


```java
@Slf4j
@Component
@RequiredArgsConstructor
public class PessoaGatewayImpl {
    
    private final PessoaRepository pessoaRepository;

    public void save(Pessoa pessoa){
        log.info("Armazenando informação.");
        pessoaRepository.save(pessoa);
    }

}
```

Resource -> Service -> Repository

O Resource chama o Service que chama o Repository


<h2> Class Instantiation </h2>

> Nesta classe é realizada a operação de instanciação da base de dados.    
> Criamos um subpaconte chamado Config, dentro dele foi criada a classe `Instantiation` que implementa `CommandLineRunner`.  


<h2> Padrão DTO </h2>

> DTO (*Data Transfer Object*): é um objeto de transferência de dados, ou seja, carrega os dados das entidades de forma simples, podendo, inclusive, "projetar" apenas alguns dados da entidade original.   


<h2> CRUD e Métodos </h2>

* CREATE

> Inserir, criar novos dados para serem inseridos no banco, todos os dados serão inseridos, porém o ID deverá ser gerado automaticamente pelo software.
> Método: POST 

* READ  

> Listar, obter, ler (Read) os dados, por meio do ID ou outro(s) atributo(s).
> Método: GET  

* UPDATE   

> Atualizar os dados existentes, aqui podemos permitir que todos os dados sejam atualizados, como podemos restringir os dados que podem ser atualizados.
> Método: PUT

* DELETE 

> Permite que os dados sejam deletados.  
> Método: DELETE
