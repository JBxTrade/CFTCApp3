import { II18n } from 'app/entities/i-18-n/i-18-n.model';
import { ISub } from 'app/entities/sub/sub.model';
import { ILink } from 'app/entities/link/link.model';
import { ILinkData } from 'app/entities/link-data/link-data.model';
import { TheRole } from 'app/entities/enumerations/the-role.model';

export interface IUnSub {
  id?: number;
  theRole?: TheRole;
  imageContentType?: string;
  image?: string;
  titleFr?: II18n | null;
  titleEn?: II18n | null;
  descriptionFr?: II18n | null;
  descriptionEn?: II18n | null;
  subs?: ISub[] | null;
  links?: ILink[] | null;
  linkData?: ILinkData[] | null;
}

export class UnSub implements IUnSub {
  constructor(
    public id?: number,
    public theRole?: TheRole,
    public imageContentType?: string,
    public image?: string,
    public titleFr?: II18n | null,
    public titleEn?: II18n | null,
    public descriptionFr?: II18n | null,
    public descriptionEn?: II18n | null,
    public subs?: ISub[] | null,
    public links?: ILink[] | null,
    public linkData?: ILinkData[] | null
  ) {}
}

export function getUnSubIdentifier(unSub: IUnSub): number | undefined {
  return unSub.id;
}
