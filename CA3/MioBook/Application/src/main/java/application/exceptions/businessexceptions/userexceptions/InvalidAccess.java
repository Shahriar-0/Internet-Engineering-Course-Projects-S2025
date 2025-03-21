package application.exceptions.businessexceptions.userexceptions;

import application.exceptions.businessexceptions.BusinessException;
import domain.entities.User;

import java.util.List;

public class InvalidAccess extends BusinessException {

	private static String message(String access) {
		return "User does not have " + access + " access!";
	}

	private static String message(List<User.Role> roles, User.Role targetRole, boolean isWhiteList) {
		if (isWhiteList)
			return "The role '" + targetRole.getValue() + "' has not appear in white list" +
					roles.stream().map(r -> " '" + r.getValue() + "'") + "!";
		else
			return "The role '" + targetRole.getValue() + "' appears in black list" +
					roles.stream().map(r -> " '" + r.getValue() + "'") + "!";
	}

	public InvalidAccess(String access) {
		super(message(access));
	}

	public InvalidAccess(List<User.Role> roles, User.Role targetRole, boolean isWhiteList) {
		super(message(roles, targetRole, isWhiteList));
	}
}
