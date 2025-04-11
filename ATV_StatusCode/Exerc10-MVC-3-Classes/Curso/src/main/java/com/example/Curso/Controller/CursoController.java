package com.example.Curso.Controller;

import com.example.Curso.banco.CursoDb;
import com.example.Curso.model.Aluno;
import com.example.Curso.model.Curso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class CursoController {

    CursoDb repository = new CursoDb();

    public ResponseEntity<List<Curso>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    public ResponseEntity<List<Curso>> getByProfessor(String nomeProfessor){
        return repository.findByProfessor(nomeProfessor);
    }

    public ResponseEntity<List<Curso>> getBySala(int sala){
        return repository.findBySala(sala);
    }

    public ResponseEntity<Curso> getById(int id){
        Curso curso = repository.getById(id);
        if (curso!= null){
            return ResponseEntity.ok(curso);
        }else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    public boolean insertBanco(Curso curso){
        return repository.insert(curso);
    }

    public ResponseEntity<ArrayList<Aluno>> insertAluno(int idCurso, Aluno aluno){
        return repository.insertAluno(idCurso, aluno);
    }

    // esta funcao fara o mesmo insert do aluno que a funcao a cima, porém com melhores práticas de programção
    public ResponseEntity<String> insertAlunoMelhorado(int idCurso, Aluno aluno){
        Curso curso = repository.getById(idCurso);
        if(curso == null){
            return "Curso não encontrado, para que aluno possa ser inserido!";
        }
        boolean result = repository.insertAlunoMelhorado(curso, aluno);
        if(result){
            return "Aluno inserido com sucesso";
        }
        return "Não foi possível inserir alunos";
    }

    public  ResponseEntity<Curso> update(int id, Curso curso){
        Curso cursoBd = repository.getById(id);
        if (cursoBd == null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        repository.update(id, curso);
        return ResponseEntity.ok(cursoBd);
    }

    public ResponseEntity<String> updateAluno(int idCurso, int idAluno, Aluno aluno){
        return repository.updateAluno(idCurso, idAluno, aluno);
    }

    public ResponseEntity<String> deletar(int id){
        Curso curso = repository.getById(id);
        if (curso == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuario com ID" + id + "nao encontrado");
        }
        repository.delete(id);
        return ResponseEntity.ok("curso" + curso.getNome()+ "deletado com sucesso");
    }

}
