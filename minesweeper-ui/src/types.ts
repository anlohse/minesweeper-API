
export interface UserData {

	id: number;

	name: string;

	lastName: string;

	email: string;

};

export interface Match {

	id: number;

    startTime: string;

    endTime: string;

    width: number;

    height: number;

    mines: number;

    minesDiscovered: number;

    cleared: number;

    status: string;

    data: string;

};