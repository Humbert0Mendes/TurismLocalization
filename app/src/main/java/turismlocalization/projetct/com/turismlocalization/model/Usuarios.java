package turismlocalization.projetct.com.turismlocalization.model;

/**
 * Created by Humberto on 27/06/2018.
 */

public class Usuarios {

    private String nome;
    private String sexo;
    private  String id;
    private int idade;
    private String contato;
    private String email;
    private String senha;

    public Usuarios() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuarios criarUsuario(String nome, String contato, int idade, String email, String senha, Usuarios user){

        user.setNome(nome);
        user.setContato(contato);
        user.setIdade(idade);
        user.setEmail(email);
        user.setSenha(senha);

        return user;
    }

}
