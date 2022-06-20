package pdc.validators;


import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;
import pdc.exceptions.IllegalWorkOrderParamException;
import pdc.exceptions.InvalidStueckNrParameterException;
import pdc.exceptions.PaNrShouldBeZeroWhenFiletringException;
import pdc.model.WorkOrderParams;
import pdc.validators.exceptions.IllegalBuendelFilteringException;

@NoArgsConstructor
@ToString
@Component
public class WorkOrderParamsValidator {

	public void validate(WorkOrderParams params) {
		switch (params.getFilterType()) {
		case BY_NR:
			validateByNr(params);
			break;
		case BY_BUENDEL:
			validateByBuendel(params);
			break;
		case BY_STUECK_NR:
			validateByStueckNr(params);
			break;
		default:
			throw new IllegalWorkOrderParamException(params);
		}
	}

	public void validateStuecke(WorkOrderParams params) {
		validateByStueckNr(params);
	}
	
	private void validateByNr(WorkOrderParams params) {
	}

	private void validateByBuendel(WorkOrderParams params) {
		if (params.getFirmaId() <= 0 || params.getStapelId() <= 0 || params.getBuendel1() <= 0) {
			throw new IllegalBuendelFilteringException(params);
		}
		if (params.getPaNrId() != 0) {
			throw new PaNrShouldBeZeroWhenFiletringException(params);
		}
	}

	private void validateByStueckNr(WorkOrderParams params) {
		if (params.getStueckNr() < 1_000_000 || params.getStueckTeilung() < 0 || params.getStueckTeilung() >= 1_000) {
			throw new InvalidStueckNrParameterException(params);
		}
		if (params.getPaNrId() != 0) {
			throw new PaNrShouldBeZeroWhenFiletringException(params);
		}
	}
}
