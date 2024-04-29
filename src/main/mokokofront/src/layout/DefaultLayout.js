import NavLayout from "./NavLayout";
import FooterLayout from "./FooterLayout";

const DefaultLayout = ({children}) => {
    return (
        <>
            <NavLayout/>
            {children}
            <FooterLayout/>
        </>
    );
}

export default DefaultLayout;