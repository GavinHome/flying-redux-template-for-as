import 'package:flying_redux/flying_redux.dart';

enum $nameAction { someAction, onSomeAction }

class $nameActionCreator {
  static Action onSomeAction(String uniqueId) {
    return Action($nameAction.onSomeAction, payload: uniqueId);
  }

  static Action someAction(dynamic state) {
    return Action($nameAction.someAction, payload: state);
  }
}
