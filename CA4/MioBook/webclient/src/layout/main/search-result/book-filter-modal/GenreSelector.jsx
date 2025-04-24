const GenreSelector = ({ genreList, value, onChange, ...props }) => {
    return (
        <select onChange={(e) => onChange(e.target.value)}
            value={value} {...props}>
            <option value="">All</option>
            {genreList.map((genre) => (
                <option key={genre} value={genre}>
                    {genre}
                </option>
            ))}
        </select>
    );
}

export default GenreSelector;
