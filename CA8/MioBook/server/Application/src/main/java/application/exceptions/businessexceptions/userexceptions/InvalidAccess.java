package application.exceptions.businessexceptions.userexceptions;

import application.exceptions.businessexceptions.BusinessException;
import domain.entities.user.Role;

import java.util.List;

public class InvalidAccess extends BusinessException {

	private static String message(String access) {
		return "User does not have " + access + " access!";
	}

	private static String message(List<Role> roles, Role targetRole, boolean isWhiteList) {
		if (isWhiteList) // TODO: update these based on new definition of white list or revert white list functionality
			return "The role '" + targetRole.getValue() + "' has not appear in white list: {" +
					getListStr(roles) + "} " + "!";
		else
			return "The role '" + targetRole.getValue() + "' appears in black list: {" +
					getListStr(roles) + "} " + "!";
	}

	public InvalidAccess(String access) {
		super(message(access));
	}

	public InvalidAccess(List<Role> roles, Role targetRole, boolean isWhiteList) {
		super(message(roles, targetRole, isWhiteList));
	}

    private static String getListStr(List<Role> roles) {
        if (roles.isEmpty())
            return "";

        StringBuilder stringBuilder = new StringBuilder();
        for (Role role : roles) {
            stringBuilder.append("'").append(role.getValue()).append("', ");
        }

        String result = stringBuilder.toString();
        return result.substring(0, result.length() - 2);
    }
}
