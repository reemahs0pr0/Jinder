package sg.edu.iss.jinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_graph")
public class User_Graph {
	@Id
	@Column(name="graph_id")
	private int id;
	@Column(name="graph_code", columnDefinition = "TEXT")
	private String code;
	
	@OneToOne
	private User user;
	
	public User_Graph() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User_Graph(String code) {
		super();
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setGraphCode(String code) {
		this.code = code;
	}
}