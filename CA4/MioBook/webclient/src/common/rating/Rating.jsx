import filledStar from "assets/icons/filled-star.svg";
import emptyStar from "assets/icons/empty-star.svg";
import halfStar from "assets/icons/half-star.svg";
import quarterStar from "assets/icons/one-fourth-filled-star.svg";
import threeQuarterStar from "assets/icons/three-fourth-filled-star.svg";

const Rating = ({ rating }) => {
    const renderStars = () => {
        const stars = [];
        for (let i = 1; i <= 5; i++) {
            if (rating >= i)
                stars.push(<img key={i} src={filledStar} alt="filled-star" />);
            else if (rating >= i - 0.75)
                stars.push(<img key={i} src={threeQuarterStar} alt="three-fourth-filled-star" />);
            else if (rating >= i - 0.5)
                stars.push(<img key={i} src={halfStar} alt="half-star" />);
            else if (rating >= i - 0.25)
                stars.push(<img key={i} src={quarterStar} alt="one-fourth-filled-star" />);
            else
                stars.push(<img key={i} src={emptyStar} alt="empty-star" />);
        }
        return stars;
    };

    return (
        <div className="d-flex flex-row">
            {renderStars()}
        </div>
    );
};

export default Rating;