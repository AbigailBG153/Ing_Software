import { Profile } from "./profile";

export interface LoginResponse {
    token: string;
    user: Profile
}
