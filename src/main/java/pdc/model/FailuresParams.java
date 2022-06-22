package pdc.model;

import lombok.Getter;
import lombok.Setter;
import pdc.exceptions.IlegalParamException;

import javax.validation.constraints.Min;

import java.util.Optional;

import static pdc.model.FailuresQueryType.*;

@Getter
@Setter
public class FailuresParams {
	private int firmaId;
	private int prodstufeId;
	private int paNrId;
	private int id;
	private int hours = 12;
	private int personalId;	
	private String buendelBc;
	private String abfallId;
	private boolean withStueckNr;
	private int count = 3;

	public FailuresParams(Optional<Integer> optionalFirmaId, Optional<Integer> optionalProdstufeId, Optional<Integer>  optionalPaNrId) {
		if (optionalFirmaId.isPresent()) {
			this.firmaId = optionalFirmaId.get();
		}
		if (optionalProdstufeId.isPresent()) {
			this.prodstufeId = optionalProdstufeId.get();
		}
		if (optionalPaNrId.isPresent()) {
			this.paNrId = optionalPaNrId.get();
		}
	}

	public void setBuendelBc(Optional<String> optionalBuendelBc) {
		if (optionalBuendelBc.isPresent()) {
			this.buendelBc = optionalBuendelBc.get();
		}
	}
	
	public void setTopParams(Optional<String> optionalAbfallId, Optional<String> optionalWithStueckNr, Optional<String> optionalCount) {
		if (optionalAbfallId.isPresent()) {
			this.abfallId = optionalAbfallId.get();
		}
		try {
			if (optionalWithStueckNr.isPresent()) {
				this.withStueckNr = Boolean.parseBoolean(optionalWithStueckNr.get());
			}
			if (optionalCount.isPresent()) {
				this.count = Integer.parseInt(optionalCount.get());
			}
		} catch (NumberFormatException nfe) {
			throw new IlegalParamException(String.format("Invalid withStueckNr (%s) or count (%s)",optionalCount.get()));
		}
	}

	public void setPersonalParams(Optional<String> optionalHours, Optional<String> optionalPersonalId) {
		try {
			if (optionalHours.isPresent()) {
				this.hours = Integer.parseInt(optionalHours.get());
			}
			if (optionalPersonalId.isPresent()) {
				this.personalId = Integer.parseInt(optionalPersonalId.get());
			}
		} catch (NumberFormatException nfe) {
			throw new IlegalParamException(String.format("Invalid hours (%s) or personalId (%s)",hours, personalId));
		}
	}

	public FailuresQueryType getFilterType() {
		if (id > 0) {
			return BY_ID;
		}
		if (personalId > 0) {
			return BY_PERSONAL;
		}
		if (count > 1) {
			return BY_TOP;
		}
		return BY_PA_NR;
	}
}
