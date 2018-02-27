package br.edu.ufcg.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.edu.ufcg.entities.Aluno;
import br.edu.ufcg.util.Validador;

/**
 * Classe que representa o sistema Quem Me Ajuda.
 * 
 * Projeto Laboratório de Programação II
 */
public class QmaSistema {
	/**
	 * Objeto que serve para validar os dados do aluno.
	 */
	private Validador validador;
	/**
	 * Mapa que relaciona a matricula de um aluno com seu objeto correspondente.
	 */
	private Map<String, Aluno> alunos;
	/**
	 * Mapa que relaciona o email de um tutor com sua matricula.
	 */
	private Map<String, String> tutores;

	public QmaSistema() {
		this.validador = new Validador();
		this.alunos = new HashMap<String, Aluno>();
		this.tutores = new HashMap<String, String>();
	}

	/**
	 * Cadastra um aluno a partir do nome,matricula,curso,email e telefone,sendo
	 * oúltimo opcional.
	 * 
	 * @param nome
	 *            String que representa o nome do aluno
	 * @param matricula
	 *            String que representa a matricula do aluno
	 * @param codigoCurso
	 *            Inteiro que representa o codigo do curso do aluno
	 * @param telefone
	 *            String que representa o telefone do aluno
	 * @param email
	 *            String que representa o email do aluno
	 */
	public void cadastrarAluno(String nome, String matricula, int codigoCurso, String telefone, String email) {
		this.validador.cadastroInvalido(nome, matricula, codigoCurso, telefone, email);

		if (alunos.containsKey(matricula)) {
			validador.matriculaCadastrada();
		}

		this.alunos.put(matricula, new Aluno(nome, matricula, codigoCurso, telefone, email));
	}

	/**
	 * Recupera um aluno a partir da sua matricula.
	 * 
	 * @param matricula
	 *            String que representa a matricula do aluno.
	 * 
	 * @return O toString do aluno.
	 */
	public String recuperaAluno(String matricula) {
		if (!alunos.containsKey(matricula)) {
			validador.alunoInexistente("Erro na busca por aluno");
		}
		return this.alunos.get(matricula).toString();
	}

	/**
	 * Faz a listagem de todos os alunos cadastrados no sistema.
	 * 
	 * @return O toString de todos os alunos
	 */
	public String listarAlunos() {
		List<Aluno> alunosOrdenados = new ArrayList<Aluno>(alunos.values());
		Collections.sort(alunosOrdenados);

		return mapToString(alunosOrdenados.stream());
	}

	/**
	 * Recupera um dos atributos de um aluno a partir da sua matricula.
	 * 
	 * @param matricula
	 *            String da matricula do aluno.
	 * @param atributo
	 *            String nome do atributo.
	 * 
	 * @return uma String que representa o atributo em questão.
	 */
	public String getInfoAluno(String matricula, String atributo) {
		if (!alunos.containsKey(matricula)) {
			validador.alunoInexistente("Erro na obtencao de informacao de aluno");
		}
		return this.alunos.get(matricula).getInfoAluno(atributo);
	}

	/**
	 * Torna um aluno em um tutor.
	 * 
	 * @param matricula
	 *            String da matricula do aluno.
	 * @param disciplina
	 *            String da disciplina em que o tutor pode ajudar.
	 * @param proficiencia
	 *            int que representa a proeficiência do tutor na disciplina, varia
	 *            de 1 a 5.
	 */
	public void tornarTutor(String matricula, String disciplina, int proficiencia) {
		if (this.tutores.containsValue(matricula)) {
			// criar "Ja eh tutor dessa disciplina"
		}
		if (proficiencia <= 0 || proficiencia > 5) {
			// criar "Proficiencia invalida"
		}
		if (!this.alunos.containsKey(matricula)) {
			validador.tutorNaoEncontrado("Erro na definicao de papel:");
		}
		this.alunos.get(matricula).tornarTutor(disciplina, proficiencia);
		this.tutores.put(this.alunos.get(matricula).getEmail(), matricula);
	}

	/**
	 * Recupera um tutor a partir da sua matricula.
	 * 
	 * @param matricula
	 *            String da matricula do tutor
	 * 
	 * @return o toString do tutor.
	 */
	public String recuperaTutor(String matricula) {
		if (!this.tutores.containsValue(matricula)) {
			validador.tutorNaoEncontrado("Erro ainda não listado");
		}
		return this.alunos.get(matricula).toString();
	}

	/**
	 * Faz a listagem dos tutores.
	 * 
	 * @return o toSTring dos tutores.
	 */
	public String listarTutores() {
		List<Aluno> alunosOrdenados = new ArrayList<Aluno>(alunos.values());
		Collections.sort(alunosOrdenados);
		return mapToString(alunosOrdenados.stream().filter(aluno -> tutores.containsKey(aluno.getEmail())));
	}

	/**
	 * Recebe um objeto Stream de Aluno, e realiza o mapeamento encadeado do
	 * toString do aluno, adicionando a String ", " a cada iteração.
	 * 
	 * @param alunos
	 *            Stream de alunos.
	 * @return uma String contendo o toString encadeado dos alunos.
	 */
	private String mapToString(Stream<Aluno> alunos) {
		return alunos.map(Aluno::toString).collect(Collectors.joining(", "));
	}

	/**
	 * Cadastra o dia e horário de atendimento de um tutor.
	 * 
	 * @param email
	 *            String do email do tutor.
	 * @param horario
	 *            String do horario de atendimento do tutor.
	 * @param dia
	 *            String do dia do atendimento do tutor.
	 */
	public void cadastrarHorario(String email, String horario, String dia) {
		if (!tutores.containsKey(email)) {
			validador.tutorNaoCadastrado("Erro ainda não listado");
		}
		this.alunos.get(this.tutores.get(email)).cadastrarHorario(horario, dia);
	}

	/**
	 * Cadastra um local de atendimento de um tutor.
	 * 
	 * @param email
	 *            String do email do tutor.
	 * @param local
	 *            String do local do atendimento do tutor.
	 */
	public void cadastrarLocalDeAtendimento(String email, String local) {
		if (!tutores.containsKey(email)) {
			validador.tutorNaoEncontrado("Erro ainda não listado");
		}
		this.alunos.get(this.tutores.get(email)).cadastrarLocalDeAtendimento(local);

	}

	/**
	 * Consulta do horario de atendimento do tutor.
	 * 
	 * @param email
	 *            String do email do tutor.
	 * @param horario
	 *            String do horario de atendimento do tutor.
	 * @param dia
	 *            String do dia do atendimento do tutor.
	 * 
	 * @return um boolean que indica se o dia e o horário passados fazem parte do
	 *         atendimento de um tutor.
	 */
	public boolean consultaHorario(String email, String horario, String dia) {
		if (!tutores.containsKey(email)) {
			validador.tutorNaoEncontrado("Erro ainda não listado");
		}
		return this.alunos.get(this.tutores.get(email)).consultaHorario(horario, dia);
	}

	/**
	 * Consulta do local de atendimento do tutor.
	 * 
	 * @param email
	 *            String do email do tutor.
	 * @param local
	 *            String do local do atendimento do tutor.
	 * 
	 * @return um boolean que indica se o local passado é o local de atendimento do
	 *         tutor.
	 */
	public boolean consultaLocal(String email, String local) {
		if (!tutores.containsKey(email)) {
			validador.tutorNaoEncontrado("Erro ainda não listado");
		}
		return this.alunos.get(this.tutores.get(email)).consultaLocal(local);
	}

}