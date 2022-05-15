package phytechExerciseClient.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ItemDto implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7521316714009942748L;

	long id;
	
	String description;
	
	float price;

}
