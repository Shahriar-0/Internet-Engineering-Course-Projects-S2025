export const getInitFormState = () => {
    return {
        username: {
            value: '',
            error: ''
        },
        password: {
            value: '',
            error: ''
        },
        email: {
            value: '',
            error: ''
        },
        country: '',
        city: '',
        role: 'customer'
    };
}

export const canSubmit = (formState) => {
    return formState.username.value && formState.password.value
        && formState.email.value && formState.country && formState.city;
}

export const hasAnyError = (formState) => {
    return formState.username.error || formState.password.error || formState.email.error;
}

export const validateForm = (formState) => {
    const state = structuredClone(formState);
    state.username.error = validateUsername(state.username.value);
    state.password.error = validatePassword(state.password.value);
    state.email.error = validateEmail(state.email.value);
    return state;
}

export const isKnownBadRequestError = (errorData) => {
    return errorData.username || errorData.email;
}

export const setError = (formState, errorData) => {
    const state = structuredClone(formState);

    if (errorData.username)
        state.username.error = errorData.username;
    else
        state.username.error = '';

    if (errorData.email)
        state.email.error = errorData.email;
    else
        state.email.error = '';

    return state;
}

export const clearError = (formState) => {
    const state = structuredClone(formState);
    state.username.error = '';
    state.password.error = '';
    state.email.error = '';
    return state;
}


const validateUsername = (username) => {
    const usernameRegex = /^[a-zA-Z0-9_-]+$/;
    if (!usernameRegex.test(username)) {
        return "Username must contain only letters, numbers, underscores, hyphens, or underscores.";
    }
    return '';
}

const validatePassword = (password) => {
    const minLength = 4;
    if (password.length < minLength) {
        return "Password must be at least " + minLength + " characters long.";
    }
    return '';
}

const validateEmail = (email) => {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailRegex.test(email)) {
        return "Invalid email format.";
    }
    return '';
}
