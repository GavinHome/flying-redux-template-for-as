import 'package:flying_redux/flying_redux.dart';

enum $nameAction { initAction, someAction, onSomeAction }

class $nameActionCreator {
  static Action initAction(dynamic data) {
    return Action($nameAction.initAction, payload: data);
  }

  static Action onSomeAction() {
    return const Action($nameAction.onSomeAction);
  }

  static Action someAction(dynamic state) {
    return Action($nameAction.someAction, payload: state);
  }
}
