import { useContext, useEffect } from 'react';
import PropTypes from 'prop-types';
import { useParams } from 'react-router-dom';
import GlobalContext from '../GlobalContext';

const IndividualPostPage = () => {
    const { id } = useParams();
    const { individualPost, loadIndividualPost } = useContext(GlobalContext);

    useEffect(() => {
        console.log("!hey")
        loadIndividualPost(id)
            .catch((error) => console.error('Error loading individual post:', error));
    }, [id, loadIndividualPost]);

    if (!individualPost) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h2>{individualPost.title}</h2>
            <p>{individualPost.content}</p>
        </div>
    );
};

IndividualPostPage.propTypes = {
    match: PropTypes.shape({
        params: PropTypes.shape({
            id: PropTypes.string.isRequired,
        }),
    }),
};

export default IndividualPostPage;
