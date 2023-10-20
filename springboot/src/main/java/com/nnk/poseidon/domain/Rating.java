package com.nnk.poseidon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * A model class which creates the POJO (Plain Old Java Object)
 * <code>Rating</code>. It contains getters and setters, as well as an override
 * <code>toSring()</code> method for display in the console.
 *
 * @singularity <code>Rating</code> is linked to the <code>rating</code> table
 *              of the database. Due to a conflict between the template and the
 *              list of financial entities of the POJO classes, the decision was
 *              made to align with the list and rectify the template
 *              accordingly.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
@Entity
@Table(name = "rating")
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank(message = "Moodys is mandatory")
	private String moodysRating;
	@NotBlank(message = "SandP is mandatory")
	private String sandPRating;
	@NotBlank(message = "Fitch is mandatory")
	private String fitchRating;
	@NotNull(message = "Order is mandatory")
	private Integer orderNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMoodysRating() {
		return moodysRating;
	}

	public void setMoodysRating(String moodysRating) {
		this.moodysRating = moodysRating;
	}

	public String getSandPRating() {
		return sandPRating;
	}

	public void setSandPRating(String sandPRating) {
		this.sandPRating = sandPRating;
	}

	public String getFitchRating() {
		return fitchRating;
	}

	public void setFitchRating(String fitchRating) {
		this.fitchRating = fitchRating;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * An override method for user-friendly display of <code>Rating</code>
	 * attributes in the console. Not necessary, except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>Rating</code>.
	 */
	@Override
	public String toString() {
		return "[" + id + "]" + "[" + moodysRating + "]" + "[" + sandPRating + "]" + "[" + fitchRating + "]" + "["
				+ orderNumber + "]";
	}
}