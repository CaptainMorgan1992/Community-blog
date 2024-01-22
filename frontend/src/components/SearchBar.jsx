// eslint-disable-next-line react/prop-types
const SearchBar = ({ searchTerm, onChange }) => {
    return (
        <div className="searchBar">
            <input
                type="text"
                placeholder="Search..."
                value={searchTerm}
                onChange={(e) => onChange(e.target.value)}
            />
        </div>
    );
};

export default SearchBar;
