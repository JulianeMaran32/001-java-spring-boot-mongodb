# Curso: Programação Orientada a Objetos com Java

[EducandoWeb](http://educandoweb.com.br)

Prof. Dr. Nelio Alves

<h2>Projeto MongoDB com Spring Boot</h2>

<h4> Objetivo geral:</h4>

<ul>
	<li>Compreender as principais diferenças entre paradigma orientado a documentos e relacional</li>
	<li>Implementar operações de CRUD</li>
	<li>Refletir sobre decisões de design para um banco de dados orientado a documentos</li>
	<li>Implementar associações entre objetos
		<ul>
			<li>Objetos aninhados</li>
			<li>Referências</li>
		</ul>
	</li>	
	<li>Realizar consultas com Spring Data e MongoRepository</li>
</ul>

<h4>Ferramentas utilizadas </h4>

* IDE: Spring Tool Suite for Eclipse    
* Database: MongoDB    
* Postman   


<h4> Instalação do MongoDB </h4>

**Checklist Windows:**

* [MongoDB](https://www.mongodb.com) -> Download -> Community Server
* Baixar e realizar a instalação com opção "Complete"
	
> ATENÇÃO: optaremos no curso por NÃO instalar o Compass por enquanto
	
* [Docs MongoDB](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/) > Set up the MongoDB environment
* Criar pasta `\data\db`
* Acrescentar em PATH: `C:\Program Files\MongoDB\Server\3.6\bin` (adapte para sua versão)
* Testar no terminal: `mongod`

**Checklist Mac:**

* [Docs MongoDB for Mac](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/)
* Instalar [brew](https://brew.sh) > executar o comando apresentado na primeira página
* Instalar o MongoDB:

```
brew install mongodb
```

* Criar pasta /data/db: 
	
```
sudo mkdir -p /data/db
```
	
* Liberar acesso na pasta criada

```
whoami (para ver seu nome de usuário, exemplo: nelio)
sudo chown -Rv nelio /data/db
```

* Testar no terminal: 

```
mongod
```

<h4> Instalação do Mongo Compass</h4>

[MongoDB Compass](https://www.mongodb.com/products/compass)


<h4> Primeiro commit - projeto criado </h4> 


> Erro comum: arquivo corrompido do Maven (invalid LOC header)
> Recomendação: apague os arquivos e voltar ao STS e deixar o Maven refazer o download    

* [Vídeo Youtube](https://www.youtube.com/watch?v=FnI1oXbDtOg)    

> File -> New -> Spring Starter Project  
  - Escolher somente o pacote Web por enquanto   

> Rodar o projeto e testar: http://localhost:8080  
> Se quiser mudar a porta padrão do projeto, incluir em `application.properties`:  


```
server.port=${port:8081}
```

* Na criação do projeto, inclui as dependências `DevTools`, `Spring Web` e `MongoDB` (ver pom.xml do projeto).  
* Após a criação, inclui a dependência do Lombok, o qual utilizo nas classes "User" e "UserDTO".  



<h4> Entity User e REST funcionando </h4>

<h6>Para criar entidades</h6>   

* Atributos básicos   
* Associações (inicie as coleções)    
* Construtores (não inclua coleções no construtor com parâmetros)   
* Getters e setters    
* hashCode e equals (implementação padrão: somente id)    
* Serializable (padrão: 1L)    


------------------------------------

<h4> Dependency: Project Lombok </h4>

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

Exemplo, utilizando as anotações citadas acima:  


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

------------------------------------

* No subpacote domain, criar a classe User
* No subpacote resources, criar uma classe UserResource e implementar nela o endpoint GET padrão:   

```java
@RestController
@RequestMapping(value="/users")
public class UserResource {
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<User>> findAll() {
		List<User> list = new ArrayList<>();
		User maria = new User("1001", "Maria Brown", "maria@gmail.com");
		User alex = new User("1002", "Alex Green", "alex@gmail.com");
		list.addAll(Arrays.asList(maria, alex));
		return ResponseEntity.ok().body(list);
	}
}
```


<h4>Conectando ao MongoDB com Repository e Service </h4>

**Referências:**  

* Spring Docs: [common application properties](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)    
* Spring Docs: [boot features nosql](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-nosql.html)     
* Stackoverflow: [mongodb](https://stackoverflow.com/questions/38921414/mongodb-what-are-the-default-user-and-password)    

* Em `pom.xml`, incluir a dependência do MongoDB:  

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

* No pacote `repository`, criar a interface `UserRepository`
* No pacote services, criar a classe `UserService` com um método `findAll`
* Em `User`, incluir a anotação `@Document` e `@Id` para indicar que se trata de uma coleção do MongoDB
* Em `UserResource`, refatorar o código, usando o `UserService` para buscar os usuários
* Em `application.properties`, incluir os dados de conexão com a base de dados:  

```
spring.data.mongodb.uri=mongodb://localhost:27017/workshop_mongo   
```

* Subir o MongoDB (comando `mongod`)   
* Usando o MongoDB Compass:   
  - Criar base de dados: `workshop_mongo`
  - Criar coleção: `user`   
  - Criar alguns documentos user manualmente   
* Testar o endpoint `/users`   









<h2> Class UserResource </h2>

> Método GET findAll lista todos os usuários   
> `ResponseEntity` encapsula toda uma estrutura que retorna response HTTP com possíveis erros e cabeçalhos  
> `List<User> list = userService.findAll();`: busca no banco de dados os usuarios  eguarda ela    
> `return ResponseEntity.ok().body(list);`  
> `ok()`: instância o ResponseEntity já com o código de resposta HTTP que é SUCCESS   
> `body()`: trás no corpo da requisição a lista 


<h2> Class UserRepository </h2>

> Faz as operações básicas como adicionar, criar, deletar, atualizar etc.     

<h2> Class UserService </h2>

> serviço responsável por trabalhar com usuários    
> Service conversa com o Repository

<h2> Database: MongoDB </h2>

> Para que o MongoDB reconheça a classe, devemos incluir `@Document` em cima da classe User e `@Id` logo acima do atributo id, também da classe User.  
> A camada REST conversa com o Service   

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

<h2> Padrão DTO </h2>

* Class UserResource

> `listDTO` recebe a conversao de cada elemento da lista original para dto; instrução lambda que vai ser: lista original ponto stream, para transformar em uma `.stream()`, que é uma coleção compatível com as expressões **lambda**; chamar o método `.map()` que vai pegar cada objeto x da lista original e para cada objeto desse que será um usuário vamos retornar um novo `UserDTO` e passando o x como argumento; para finalizar, precisamos voltar o `.stream()` para uma lista.

<h2> Obter usuário por ID </h2>

* subpacote: service.exception   
* class: ObjectNotFoundException   

> Criar uma exceção personalizada, para ser disparada quando for buscar o usuário por id, e o id fornecido for inexistente.   
> `extends RuntimeExceptions`: NÃO exige que seja feito o tratamento da excecao


> Anotação `@PathVariable` indica que o `String id` é o mesmo que está sendo passado chamado dentro do método Get  

```java
@GetMapping(value="/{id}")
public ResponseEntity<UserDTO> findById(@PathVariable String id){
}
```

No subpacote resources.exception, criar as classes:
- StandardError
- ResourceExceptionHandler
