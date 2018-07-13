package turismlocalization.projetct.com.turismlocalization.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Humberto on 27/06/2018.
 */

public class Usuarios {

    public String nome;
    public String sexo;
    public  String id;
    public int idade;
    public String contato;
    public String email;
    public String senha;

    public Usuarios() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public  Usuarios(String nome, String sexo, String id, int idade, String contato, String email, String senha){
        this.nome = nome;
        this.id = id;
        this.sexo = sexo;
        this.contato = contato;
        this.idade = idade;
        this.email = email;
        this.senha = senha;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nome", nome);
        result.put("sexo", sexo);
        result.put("id", id);
        result.put("contato", contato);
        result.put("idade", idade);
        result.put("email", email);
        result.put("senha", senha);

        return result;
    }
    // [END post_to_map]


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
