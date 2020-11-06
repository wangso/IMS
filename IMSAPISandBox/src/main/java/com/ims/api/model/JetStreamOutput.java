package com.ims.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jetstream_output")
public class JetStreamOutput {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "output_id")
	private Long outputId;

	@Column(name = "output")
	private String output;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "module_id")
	private Long moduleId;


	@Column(name = "script_name")
	private String scriptName;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "faliuar_details")
	private String faliuarDetails;

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFaliuarDetails() {
		return faliuarDetails;
	}

	public void setFaliuarDetails(String faliuarDetails) {
		this.faliuarDetails = faliuarDetails;
	}

	public String getScriptName() {
		return scriptName;
	}

	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	public Long getId() {
		return this.outputId;
	}

	public Long getOutputId() {
		return this.outputId;
	}

	public void setOutputId(Long outputId) {
		this.outputId = outputId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public void setId(Long outputId) {
		this.outputId = outputId;
	}

	public String getOutput() {
		return this.output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
}
