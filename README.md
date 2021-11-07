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

------------------------------------

<h4>Ferramentas utilizadas </h4>

* IDE: Spring Tool Suite for Eclipse    
* Database: MongoDB    
* Postman   

------------------------------------

<h4> Instalação do MongoDB </h4>

<h6>Checklist Windows:</h6>

* [MongoDB](https://www.mongodb.com) -> Download -> Community Server
* Baixar e realizar a instalação com opção "Complete"
* Instalação do [MongoDB Compass](https://www.mongodb.com/products/compass)
* [Docs MongoDB](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/) > Set up the MongoDB environmen
* Criar pasta `\data\db
* Acrescentar em PATH: `C:\Program Files\MongoDB\Server\3.6\bin` (adapte para sua versão)
* Testar no terminal: `mongod`

<h6>Checklist Mac:</h6>

* [Docs MongoDB for Mac](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/)
* Instalar [brew](https://brew.sh) > executar o comando apresentado na primeira págin
* Instalar o MongoDB: `brew install mongod``
* Criar pasta /data/db: `sudo mkdir -p /data/d``
* Liberar acesso na pasta criada: `whoami` (para ver seu nome de usuário, exemplo: nelio)   

```
sudo chown -Rv nelio /data/db
````

* Testar no terminal: `mongo`

------------------------------------

<h4> Primeiro commit - projeto criado </h4> 


> Erro comum: arquivo corrompido do Maven (invalid LOC header)
> Recomendação: apague os arquivos e voltar ao STS e deixar o Maven refazer o download    
>
> Ver mais: [Vídeo Youtube](https://www.youtube.com/watch?v=FnI1oXbDtOg)    
>
> File -> New -> Spring Starter Project  
  - Escolher somente o pacote Web por enquanto   

> Rodar o projeto e testar: http://localhost:8080  
> Para alterar a porta padrão, deve incluir no arquivo `application.properties` a seguinte linha: `server.port=${port:8081}`    

* Na criação do projeto, inclui as dependências `DevTools`, `Spring Web` e `MongoDB` (ver pom.xml do projeto).  
* Após a criação, inclui a dependência do Lombok, o qual utilizo nas classes "User" e "UserDTO".  

------------------------------------

<h4> Entity User e REST funcionando </h4>

<h6>Para criar entidades</h6>   

* Atributos básicos   
* Associações (inicie as coleções)    
* Construtores (não inclua coleções no construtor com parâmetros)   
* Getters e setters    
* hashCode e equals (implementação padrão: somente id)    
* Serializable (padrão: 1L)    

> Neste projeto foi utilizado o Lombok, ver tópico abaixo:

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

<h6> Referências </h6>

* Spring Docs: [common application properties](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)    
* Spring Docs: [boot features nosql](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-nosql.html)     
* Stackoverflow: [mongodb](https://stackoverflow.com/questions/38921414/mongodb-what-are-the-default-user-and-password)    


<h6> Checklist: </h6>

<ul>
	<li>Em `pom.xml`, incluir a dependência do MongoDB:</li>
</ul>

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

<ul>
	<li>No pacote repository, criar a interface UserRepository</li>
	<li>No pacote services, criar a classe `UserService` com um método `findAll`</li>
	<li>Em `User`, incluir a anotação `@Document` e `@Id` para indicar que se trata de uma coleção do MongoDB</li>
	<li>Em `UserResource`, refatorar o código, usando o `UserService` para buscar os usuários</li>
	<li>Em `application.properties`, incluir os dados de conexão com a base de dados:</li>
</ul>


```
spring.data.mongodb.uri=mongodb://localhost:27017/workshop_mongo   
```

<ul>
	<li>Subir o MongoDB (comando `mongod`)</li>
	<li>Usando o MongoDB Compass:
		<ul>
			<li>Criar base de dados: `workshop_mongo`</li>
			<li>Criar coleção: `user`</li>
			<li>Criar alguns documentos user manualmente</li>
		</ul>
	</li>
	<li>Testar o endpoint `/users`</li>
</ul>



--------------------------

<h2> Operação de instanciação da base de dados </h2>

```java
userReposiroty.saveAll(Arrays.asList(maria, alex, bob));
```

<h6>Checklist</h6>

<ul>
	<li>No subpacote config, criar uma classe de configuração Instantiation que implemente CommandlLineRunner</li>
	<li>Dados para copiar:</li>
</ul>

```java
User maria = new User(null, "Maria Brown", "maria@gmail.com");
User alex = new User(null, "Alex Green", "alex@gmail.com");
User bob = new User(null, "Bob Grey", "bob@gmail.com");
```

--------------------------

<h2>Notas:</h2>
<h6> Class UserResource </h6>

> Método GET findAll lista todos os usuários   
> `ResponseEntity` encapsula toda uma estrutura que retorna response HTTP com possíveis erros e cabeçalhos  
> `List<User> list = userService.findAll();`: busca no banco de dados os usuarios  eguarda ela    
> `return ResponseEntity.ok().body(list);`  
> `ok()`: instância o ResponseEntity já com o código de resposta HTTP que é SUCCESS   
> `body()`: trás no corpo da requisição a lista 

<h6> Class UserRepository </h6>

> Faz as operações básicas como adicionar, criar, deletar, atualizar etc.     

<h6> Class UserService </h6>

> serviço responsável por trabalhar com usuários    
> Service conversa com o Repository

<h6> Database: MongoDB </h6>

> Para que o MongoDB reconheça a classe, devemos incluir `@Document` em cima da classe User e `@Id` logo acima do atributo id, também da classe User.  
> A camada REST conversa com o Service   

Resource -> Service -> Repository

O Resource chama o Service que chama o Repository


<h6> Class Instantiation </h6>

> Nesta classe é realizada a operação de instanciação da base de dados.    
> Criamos um subpaconte chamado Config, dentro dele foi criada a classe `Instantiation` que implementa `CommandLineRunner`.  

------------
	
<h2> Usando adrão DTO para retornar usuários </h2>

**DTO (Data Transfer Object)**: é um objeto que tem o papel de carregar dados das entidades de forma simples, podendo inclusive "projetar" apenas alguns dados da entidade original.  
Vantagens:

> - Otimizar o tráfego (trafegando menos dados)   
> - Evitar que dados de interesse exclusivo do sistema fiquem sendo expostos (por exemplo: senhas, dados de auditoria como data de criação e data de atualização do objeto, etc.)
> - Customizar os objetos trafegados conforme a necessidade de cada requisição (por exemplo: para alterar um produto, você precisa dos dados A, B e C; já para listar os produtos, eu preciso dos dados A, B e a categoria de cada produto, etc.).   


<h6>Checklist</h6>

<ul>
	<li>No subpacote dto, criar UserDTO</li>
	<li>Em UserResource, refatorar o método findAl</li>
</ul>

<h6> Class UserResource</h6>

> `listDTO` recebe a conversao de cada elemento da lista original para dto; instrução lambda que vai ser: lista original ponto stream, para transformar em uma `.stream()`, que é uma coleção compatível com as expressões **lambda**; chamar o método `.map()` que vai pegar cada objeto x da lista original e para cada objeto desse que será um usuário vamos retornar um novo `UserDTO` e passando o x como argumento; para finalizar, precisamos voltar o `.stream()` para uma lista.

----------------

<h2> Notas: </h2>

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


------------

<h2>Obtendo usuário por ID</h2>


```java
import java.util.Optional;

// (...)

public User findById(String id) {
	Optional<User> obj = repo.findById(id);
	return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
}
```

<h6>Checklist</h6>

<ul>
	<li>No subpacote service.exception, criar ObjectNotFoundException</li>
	<li>Em UserService, implementar o método findById</li>
	<li>Em UserResource, implementar o método findById (retornar DTO)</li>
	<li>No subpacote resources.exception, criar as classes:
		<ul>
			<li>StandardError</li>
			<li>ResourceExceptionHandler</li>
		</ul>
	</li>
</ul>

<h6>Notas</h6>

> Criar uma exceção personalizada, para ser disparada quando for buscar o usuário por id, e o id fornecido for inexistente.   

> `extends RuntimeExceptions`: NÃO exige que seja feito o tratamento da exceção


> Anotação `@PathVariable` indica que o `String id` é o mesmo que está sendo passado chamado dentro do método Get  

```java
@GetMapping(value="/{id}")
public ResponseEntity<UserDTO> findById(@PathVariable String id){
}
```

--------------------

<h2> Inserção de usuário com POST </h2>

<h6>Checklist</h6>

<ul>
	<li>Em UserService, implementar os métodos insert e fromDTO</li>
	<li>Em UserResource, implementar o método insert</li>
</ul>

--------------------

<h2> Deleção de usuário com DELETE </h2>

```java
repo.deleteById(id);
```

<h6>Checklist</h6>

<ul>
	<li>Em UserService, implementar o método delete</li>
	<li>Em UserResource, implementar o método delete</li>
</ul>

--------------------

<h2> Atualização de usuário com PUT </h2>

(User update with PUT)

```java
public class UserService(){

	// (...)

	public User update(User obj) {
		User newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

}
```

<h6>Checklist</h6>

<ul>
	<li>Em UserService, implementar os métodos update e updateData</li>
	<li>Em UserResource, implementar o método update</li>
</ul>

--------------------

<h2> Criando entity Post com User aninhado </h2>

(Creating Entity Post)

> Nota: objetos aninhados vs. referências

<h6>Checklist</h6>

<ul>
	<li>Criar classe Post</li>
	<li>Criar PostRepository</li>
	<li>Inserir alguns posts na carga inicial da base de dados</li>
</ul>


--------------------

<h2> Projeção dos dados do autor com DTO </h2>

(Projection of author data with DTO)

<h6>Checklist</h6>

<ul>
	<li>Criar AuthorDTO</li>
	<li>Refatorar Post</li>
	<li>Refatorar a carga inicial do banco de dados
		<ul>
			<li>IMPORTANTE: agora é preciso persistir os objetos User antes de relacionar
			</li>
		</ul>
	</li>
</ul>

--------------------

<h2> Referenciando os posts do usuário </h2>

(Referencing user posts)


<h6>Checklist</h6>

<ul>
	<li>Em User, criar o atributo "posts", usando @DBRef
		<ul>
			<li>Sugestão: incluir o parâmetro (lazy = true)</li>
		</ul>
	</li>
	<li>Refatorar a carga inicial do banco, incluindo as associações dos posts</li>
</ul>

--------------------

<h2> Endpoint para retornar os posts de um usuário </h2>

(Endpoint to return a user's posts)

<h6>Checklist</h6>

* Em UserResource, criar o método para retornar os posts de um dado usuário


--------------------

<h2> Obtendo um post por id </h2>

(Getting one post per id)

<h6>Checklist</h6>

* Criar PostService com o método findById    
* Criar PostResource com método findById    

--------------------

<h2> Acrescentando comentários aos posts </h2>

(Adding comments to posts)

<h6>Checklist</h6>

* Criar CommentDTO    
* Em Post, incluir uma lista de CommentDTO   
* Refatorar a carga inicial do banco de dados, incluindo alguns comentários nos posts  


--------------------

<h2> Acrescentando comentários aos posts </h2>






